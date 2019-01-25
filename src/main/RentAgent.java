package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
import messages.AllLocations;
import messages.LocationAndAmountOfPeople;
import messages.LocationAndOffice;
import messages.NoOffice;
import messages.OfficeAvailability;
import messages.OfficeRented;
import messages.OfficesFromLocation;
import messages.OfficesLocation;
import messages.Release;
import messages.ReleaseOffice;
import messages.RenterLocations;
import messages.RenterRentOffice;
import messages.WaitForOffice;

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
          getSender().tell(new AllLocations(location), getSelf());

        }).match(OfficesLocation.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            getSender().tell(new OfficesFromLocation(location.getOffices()), getSelf());
          }
        }).match(LocationAndAmountOfPeople.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            for (Office office : location.getOffices()) {
              if (office.getAvailable(message.getAmountOfPeople())) {
                getSender().tell(new OfficeAvailability(true, office), getSelf());
              } else {
                getSender().tell(new OfficeAvailability(false, office), getSelf());
              }
            }
          }
        }).match(LocationAndOffice.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            for (Office office : location.getOffices()) {
              if (office.getName().equals(message.getOfficeName())) {
                if (office.getAvailable(message.getAmountOfPeople())) {
                  office.rent(getSender());
                  getSender().tell(new OfficeRented(true, office), getSelf());
                } else {
                  getSender().tell(new OfficeRented(false, office), getSelf());
                }
              }
            }
          }
        }).match(WaitForOffice.class, message -> {
          if (message.getiWantToWaitForOffice()) {
            message.getOffice().addWaitingRenter(getSender());
          }
        }).match(Release.class, message -> {
          for (Office office : location.getOffices()) {
            if (office.getCurrentRenter() != null) {
              if (office.getCurrentRenter().equals(getSender())) {
                office.release();
                getSender().tell(new Release(), getSelf());
              }
              else {
                getSender().tell(new NoOffice(), getSelf());
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