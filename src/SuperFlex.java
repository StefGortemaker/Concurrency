import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class SuperFlex {

  public ActorSystem actorSystem;
  public static ArrayList<Location> locations;
  ActorRef rentAgentEnschede;
  ActorRef renter;

  public static void main(String[] args) {
    SuperFlex superFlex = new SuperFlex();
    superFlex.run();
  }

  private void run() {
    actorSystem = ActorSystem.create("SuperFlex-App");
    createLocations();

    rentAgentEnschede = actorSystem.actorOf(RentAgent.prop("Enschede", locations.get(0)));
    //ActorRef rentAgentDeventer = actorSystem.actorOf(RentAgent.prop("Deventer", locations.get(1)));
    renter = actorSystem.actorOf(Renter.prop(rentAgentEnschede), "renter");

    printMainMenu();

    //System.out.println("is there a office available?");
    //rentAgentEnschede.tell(Message.RENTER_WANT_A_OFFICE, renter);
    //rentAgentEnschede.tell(Message.I_WANT_A_LIST_OF_OFFICES, renter);
  }

  private Location locationCheck(String name) {
    for (Location location : locations) {
      if (location.getName().equals(name)) {
        return location;
      }
    }
    return null;
  }

  private void createLocations() {
    locations = new ArrayList<>(5);
    for (int i = 0; i < 5; i++) {
      locations.add(new Location("Location " + i));
    }
  }

  private ArrayList<Location> getLocations() {
    return locations;
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
        rentAgentEnschede.tell(Message.I_WANT_A_LIST_OF_LOCATIONS, renter);
        break;
      case 2:
        System.out.println("From which location? (1-100)");
        locationName = "Location " + scanner.nextInt();
        rentAgentEnschede.tell(Message.I_WANT_A_LIST_OF_OFFICES, renter);
        break;
      case 3:
        System.out.println("From which location? (1-100)");
        locationName = "Location " + scanner.nextInt();
        System.out.println("And which office you want? (a number)");
        String officeName = "office " + scanner.nextInt();
        rentAgentEnschede.tell(Message.I_WANT_TO_RENT_OFFICE, renter);
        break;
      case 4:
        rentAgentEnschede.tell(Message.I_WANT_TO_RELEASE_A_RENTED_OFFICE, renter);
        break;
      case 5:
        System.exit(1);
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
