package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
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
 *
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class Renter extends AbstractActor {

  //private ActorRef rentAgent;
  private String name;

  private Renter(String name) {
    //this.rentAgent = rentAgent;
    this.name = name;
  }

  public static Props prop(String name) {
    return Props.create(Renter.class, name);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(AllLocations.class, message -> {
          System.out.println(message.getLocation().getName());

          //when a renter wants a list of all loctaions
        }).match(OfficesFromLocation.class, message -> {
          for (Office office : message.getOffices()) {
            System.out.println(office.getName());
          }
        }).match(OfficeAvailability.class, message -> {
          if (message.getOfficeAvaibable()) {
            System.out.println(message.getOffice().getName() + " is available.");
          }
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
        }).match(ReleaseOffice.class, message -> {
          System.out
              .println(message.getOffice().getName() + " you were waiting on has been released");
        }).match(Release.class, message -> {
          System.out.println("Your office has been released.");
        }).match(NoOffice.class, message -> {
          System.out.println("You don't have an office to release.");
        }).match(String.class, System.out::println).build();
  }

  @Override
  public void preStart() {
    System.out.println("main.Renter started");
  }

  public void postStop() {
    System.out.println("main.Renter exiting");
  }
}