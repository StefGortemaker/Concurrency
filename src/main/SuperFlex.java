package main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import messages.LocationAndOffice;
import messages.OfficesLocation;
import messages.RenterLocations;

public class SuperFlex {

  public ActorSystem actorSystem;
  ActorRef rentAgentEnschede;
  ActorRef renter;
  ArrayList<ActorRef> rentAgents = new ArrayList<>();

  public static void main(String[] args) {
    SuperFlex superFlex = new SuperFlex();
    superFlex.run();
  }

  private void run() {
    actorSystem = ActorSystem.create("SuperFlex-App");

    for (int i = 1; i < 3 + 1; i++){
      rentAgents.add(actorSystem.actorOf(RentAgent.prop("RentAgent_" + i, new Location("Location_" + i, "RentAgent_" + i))));
    }

    renter = actorSystem.actorOf(Renter.prop("renter"));

    printMainMenu();

    //System.out.println("is there a office available?");
    //rentAgentEnschede.tell(main.Message.RENTER_WANT_A_OFFICE, renter);
    //rentAgentEnschede.tell(main.Message.I_WANT_A_LIST_OF_OFFICES, renter);
  }

  private void printMainMenu() {
    System.out.println("What do you want to do? (1-5)");
    System.out.println("1. I want a list of locations");
    System.out.println("2. I want a list of offices of a location");
    System.out.println("3. I want to rent a office");
    System.out.println("4. I want to release my reserved office");
    System.out.println("5. Quit \n");
    getChoice();
  }

  private void getChoice() {
    String locationName;
    int choice = 5;
    Scanner  scanner = new Scanner(System.in);
    try {
      choice = scanner.nextInt();
    } catch (InputMismatchException ime) {
      ime.getMessage();
    }
    switch (choice) {
      case 1:
        for (ActorRef actorRef : rentAgents){
          actorRef.tell(new RenterLocations(), renter);
        }
        //rentAgentEnschede.tell(Message.I_WANT_A_LIST_OF_LOCATIONS, renter);
        break;
      case 2:
        System.out.println("From which location? (1-100)");
        locationName = "Location_" + scanner.nextInt();
        for (ActorRef actorRef : rentAgents){
          actorRef.tell(new OfficesLocation(locationName), renter);
        }
        break;
      case 3:
        System.out.println("From which location? (1-100)");
        locationName = "Location_" + scanner.nextInt();
//        System.out.println("And which office you want? (a number)");
//        String officeName = "Office_" + scanner.nextInt();
        System.out.println("And with how many people are you?");
        int amountOfPeople = scanner.nextInt();
        for (ActorRef actorRef : rentAgents){
          actorRef.tell(new LocationAndOffice(locationName, amountOfPeople), renter);
        }
        break;
      case 4:
        rentAgentEnschede.tell(Message.I_WANT_TO_RELEASE_A_RENTED_OFFICE, renter);
        break;
      case 5:
        actorSystem.terminate();

        //System.exit(1);
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
