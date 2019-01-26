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
import messages.RenterLocations;
import messages.RenterRentOffice;
import messages.WaitForOffice;

/**
 * The rentAgent class
 * Extends from AbstractActor
 * Handles messages from the renters
 *
 * @Author Vincent Witten & Stef Gortemaker
 */

public class RentAgent extends AbstractActor {

  private Location location;

  /**
   * Constructor for the rent agent
   *
   * @param location he belongs to
   */
  private RentAgent(Location location) {
    this.location = location;
  }

  /**
   * Creates a new rent agent
   *
   * @param location he belongs to
   * @return the created rent agent
   */
  public static Props prop(Location location) {
    return Props.create(RentAgent.class, location);
  }

  /**
   * Handles the incoming and outcoming messages
   *
   * @return a new message
   */
  @Override
  public Receive createReceive() {
    return receiveBuilder()
        //when a renter wants to rent a office
        .match(RenterRentOffice.class, message -> {
          System.out.println("rented " + message.getPlacesToRent());

          //when a renter wants a list of all loctaions
        }).match(RenterLocations.class, message -> {
          getSender().tell(new AllLocations(location), getSelf());

          //when a renter wants a list of offices of a location
        }).match(OfficesLocation.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            //tell renter the list of offices
            getSender().tell(new OfficesFromLocation(location.getOffices()), getSelf());
          }

          //when a renter wants to know if there are any offices available
        }).match(LocationAndAmountOfPeople.class, message -> {
          if (location.getName().equalsIgnoreCase(message.getLocationName())) {
            for (Office office : location.getOffices()) {
              if (office.getAvailable(message.getAmountOfPeople())) {
                //
                getSender().tell(new OfficeAvailability(true, office), getSelf());
              } else {
                getSender().tell(new OfficeAvailability(false, office), getSelf());
              }
            }
          }

          //when a renter wants to rent an office
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

          // when a renter wants to wait for an office
        }).match(WaitForOffice.class, message -> {
          if (message.getiWantToWaitForOffice()) {
            message.getOffice().addWaitingRenter(getSender());
          }

          //when a renter wants to release his office
        }).match(Release.class, message -> {
          for (Office office : location.getOffices()) {
            if (office.getCurrentRenter() != null) {
              if (office.getCurrentRenter().equals(getSender())) {
                office.release();
                getSender().tell(new Release(), getSelf());
              } else {
                getSender().tell(new NoOffice(), getSelf());
              }
            }
          }
        }).match(String.class, System.out::println).build();
  }

  //a sign that he started
  @Override
  public void preStart() {
    System.out.println("RentAgent started");
  }

  //a sign that he stopped
  public void postStop() {
    System.out.println("RentAgent exiting");
  }
}