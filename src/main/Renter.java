package main;

import akka.actor.AbstractActor;
import java.util.Scanner;
import messages.AllLocations;
import messages.NoOffice;
import messages.OfficeAvailability;
import messages.OfficeRented;
import messages.OfficesFromLocation;
import messages.Release;
import messages.ReleaseOffice;
import messages.WaitForOffice;

/**
 * This class represents the Renter
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class Renter extends AbstractActor {

  /**
   * based on the message that got received the actor will do different things look inline comments
   * for more detail
   *
   * @return the result of the message that got received
   */
  @Override
  public Receive createReceive() {
    return receiveBuilder()
        //contains the location of the RentAgent
        .match(AllLocations.class, message -> {
          System.out.println(message.getLocation().getName());
          //when a renter wants a list of all locations
        }).match(OfficesFromLocation.class, message -> {
          for (Office office : message.getOffices()) {
            System.out.println(office.getName());
          }
          //gets all office with their availability
        }).match(OfficeAvailability.class, message -> {
          if (message.getOfficeAvaibable()) {
            System.out.println(message.getOffice().getName() + " is available.");
          }
          //gets the requested office and sees if it is rented or not
        }).match(OfficeRented.class, message -> {
          if (message.getOfficeRented()) {
            System.out.println("Office rented.");
          } else {
            System.out.println("Office not rented, do you want to wait for it? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("Y")) {
              getSender().tell(new WaitForOffice(true, message.getOffice()), getSelf());
            } else if (input.equalsIgnoreCase("N")) {
              getSender().tell(new WaitForOffice(false, message.getOffice()), getSelf());
            } else {
              System.out.println("Not an optional answer.");
            }
          }
          //if someone released the office and it became available it send its message to the first renter waiting.
        }).match(ReleaseOffice.class, message -> {
          System.out
              .println(message.getOffice().getName() + " you were waiting on has been released");
          //gets conformation if the office has been released
        }).match(Release.class, message -> {
          System.out.println("Your office has been released.");
          //if you tried to release an office that you dont occupied.
        }).match(NoOffice.class, message -> {
          System.out.println("You don't have an office to release.");
          //if something gets send we did not expect.
        }).match(String.class, System.out::println).build();
  }

  /**
   * before the Actor get started a message will be displayed
   */
  @Override
  public void preStart() {
    System.out.println("main.Renter started");
  }

  /**
   * before the Actor get stopped a message will be displayed
   */
  @Override
  public void postStop() {
    System.out.println("main.Renter exiting");
  }
}