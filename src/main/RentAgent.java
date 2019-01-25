package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
import messages.AllLocations;
import messages.LocationAndOffice;
import messages.OfficeAvailability;
import messages.OfficesFromLocation;
import messages.OfficesLocation;
import messages.RenterLocations;
import messages.RenterRentOffice;

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
        //when a renter wants to rent a office
        .match(RenterRentOffice.class, message -> {
          System.out.println("rented " + message.getPlacesToRent());

          //when a renter wants a list of all loctaions
        }).match(RenterLocations.class, message -> {
          /*for (Location location : locations){
            System.out.println(name + " " + location.getName());
          }*/
          getSender().tell(new AllLocations(location), getSelf());

        }).match(OfficesLocation.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            getSender().tell(new OfficesFromLocation(location.getOffices()), getSelf());
          }
        }).match(LocationAndOffice.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            for(Office office : location.getOffices()) {
              if (office.getAvailable(message.getAmountOfPeople())){
                getSender().tell(new OfficeAvailability(true, office), getSelf());
              } else {
                getSender().tell(new OfficeAvailability(false, office), getSelf());
              }
            }
          }
        }).match(String.class, System.out::println).build();
  }

  @Override
  public void preStart() {
    System.out.println("main.RentAgent started");
  }

  public void postStop() {
    System.out.println("main.RentAgent exiting");
  }

  public Location getLocation() {
    return location;
  }

  public Location getAvailableLocation(int placesToRent) {
    if (location.checkAvailabilityOffice(placesToRent)) {
      return location;
    }
    return null;
  }
}