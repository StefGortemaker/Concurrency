package main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import messages.LocationAndAmountOfPeople;
import messages.LocationAndOffice;
import messages.OfficesLocation;
import messages.Release;
import messages.RenterLocations;

/**
 * SuperFlex class is our main class of this project.
 *
 * @Author Vincent Witten & Stef Gortemaker
 */

public class SuperFlex {

  private ActorSystem actorSystem;
  private ActorRef renter;
  private ArrayList<ActorRef> rentAgents = new ArrayList<>();

  public static void main(String[] args) {
    SuperFlex superFlex = new SuperFlex();
    superFlex.run();
  }

  private void run() {
    actorSystem = ActorSystem.create("SuperFlex-App");

    //creates all the rentAgents
    for (int i = 1; i < 100 + 1; i++) {
      rentAgents.add(actorSystem.actorOf(
          RentAgent.prop( new Location("Location_" + i))));
    }

    //creates the renter
    renter = actorSystem.actorOf(Renter.prop("renter"));

    printMainMenu();
  }

  /**
   * Prints a option menu for the renter
   */
  private void printMainMenu() {
    System.out.println("What do you want to do? (1-5)");
    System.out.println("1. I want a list of locations");
    System.out.println("2. I want a list of offices of a location");
    System.out.println("3. I want to rent a office");
    System.out.println("4. I want to release my reserved office");
    System.out.println("5. Quit \n");
    getChoice();
  }

  /**
   * Gets the choice of the renter in the option menu
   */
  private void getChoice() {
    String locationName;
    int choice = 5;
    Scanner scanner = new Scanner(System.in);

    //try to get the choice number
    try {
      choice = scanner.nextInt();
    } catch (InputMismatchException ime) {
      ime.getMessage();
    }

    //switch case for the choice of the renter
    switch (choice) {
      case 1:
        for (ActorRef actorRef : rentAgents) {
          //tell the rentAgent that the renter want a list of locations
          actorRef.tell(new RenterLocations(), renter);
        }
        break;
      case 2:
        System.out.println("From which location? (1-100)");
        locationName = "Location_" + scanner.nextInt();
        for (ActorRef actorRef : rentAgents) {
          //tell the rentAgent that the renter wants a list of offices of a location
          actorRef.tell(new OfficesLocation(locationName), renter);
        }
        break;
      case 3:
        System.out.println("From which location? (1-100)");
        locationName = "Location_" + scanner.nextInt();
        System.out.println("And with how many people are you?");
        int amountOfPeople = scanner.nextInt();
        for (ActorRef actorRef : rentAgents) {
          //tell the rentAgent that the renter wants to know if there are any offices available
          actorRef.tell(new LocationAndAmountOfPeople(locationName, amountOfPeople), renter);
        }
        System.out.println("wich one do you want to rent?(1-50)");
        String officeName = "Office_" + scanner.nextInt();
        for (ActorRef actorRef : rentAgents) {
          //tell the rentAgent that the renter wants to rent an office
          actorRef.tell(new LocationAndOffice(locationName, officeName, amountOfPeople), renter);
        }
        break;
      case 4:
        for (ActorRef actorRef : rentAgents) {
          //tell the rentAgent that the renter wants to release his office
          actorRef.tell(new Release(), renter);
        }
        break;
      case 5:
        //quit
        actorSystem.terminate();
      default:
        System.out.println("That's not an option.");
        printMainMenu();
        getChoice();
        break;
    }
    try {
      TimeUnit.SECONDS.sleep(1);
      printMainMenu();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
