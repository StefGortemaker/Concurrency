package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
import java.util.ArrayList;
import messages.AllLocations;
import messages.RenterLocations;
import messages.RenterRentOffice;

public class RentAgent extends AbstractActor {

  private String name;
  private ArrayList<Location> locations;

  private RentAgent(String name) {
    this.name = name;
    this.locations = new ArrayList<>();

    for (int i = 0; i < 3; i++){
      locations.add(new Location("Location_" + i));
    }
  }

  public static Props prop(String name) {
    return Props.create(RentAgent.class, name);
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
          getSender().tell(new AllLocations(locations), getSelf());

        }).match(String.class, System.out::println).build();
  }

  @Override
  public void preStart() {
    System.out.println("main.RentAgent started");
  }

  public void postStop() {
    System.out.println("main.RentAgent exiting");
  }

  public ArrayList<Location> getLocations() {
    return locations;
  }

  public void addLocation(Location locations) {
    this.locations.add(locations);
  }

  public ArrayList<Location> getAvailableLocations(int placesToRent){

    ArrayList<Location> avaiableLocations = new ArrayList<>();

    for (Location location : locations){
      if (location.checkAvailabilityOffice(placesToRent)){
        avaiableLocations.add(location);
      }
    }
    return avaiableLocations;
  }
}
