import akka.actor.AbstractActor;
import akka.actor.Props;

public class RentAgent extends AbstractActor {

  private String name;
  private Location location;

  private RentAgent(String name, Location location) {
    this.name = name;
    this.location = location;
  }

  public static Props prop(String name, Location location) {
    return Props.create(RentAgent.class, name, location);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder()
        .match(Message.class, message -> {
          switch (message) {
            case RENTER_WANT_A_OFFICE:
              System.out.println("Alright, let me check if it is available");

              if (location.checkAvailabilityOffice()) {
                getSender().tell(Message.OFFICE_AVAILABLE, getSelf());
              } else {
                getSender().tell(Message.OFFICE_NOT_AVAILABLE, getSelf());
              }
              break;
            case I_WANT_TO_RENT_OFFICE:
              System.out.println("Office rented");
              break;
            case I_DONT_WANT_TO_RENT_OFFICE:
              System.out.println("Office not rented");
              break;
            case I_WANT_TO_RESERVE:
              System.out.println("Office is reserved");
              break;
            case I_DONT_WANT_TO_RESERVE:
              System.out.println("Office is not reserved");
              break;
            case I_WANT_A_LIST_OF_LOCATIONS:
              System.out.println("Here are the locations: ");
              showLocations();
              getSender().tell(Message.HERE_IS_A_LIST_OF_LOCATIONS, getSelf());
              break;
            case I_WANT_A_LIST_OF_OFFICES:
              System.out.println("Here are the offices: ");
              showOffices(this.location.getName());
              getSender().tell(Message.HERE_IS_A_LIST_OF_OFFICES, getSelf());
              break;

            default:
              System.out.println("RentAgent Default");
          }
        }).match(String.class, System.out::println).build();
  }

  @Override
  public void preStart() {
    System.out.println("RentAgent started");
  }

  public void postStop() {
    System.out.println("RentAgent exiting");
  }

  private void showOffices(String locationName) {
    for (Location location : SuperFlex.locations) {
      if (location.getName().equals(locationName)) {
        for (Office office : location.getOffices()) {
          System.out.println(office.getName());
        }
      }
    }
  }

  private void showAvailableOffices(String locationName) {
    for (Location location : SuperFlex.locations) {
      if (location.getName().equals(locationName)) {
        for (Office office : location.getOffices()) {
          if(office.getAvailable()) {
            System.out.println(office.getName());
          }
        }
      }
    }
  }

  private void showLocations() {
    for (Location location : SuperFlex.locations) {
      System.out.println(location.getName());
    }
  }
}
