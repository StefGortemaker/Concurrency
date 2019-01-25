package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
import messages.AllLocations;
import messages.OfficeAvailability;
import messages.OfficesFromLocation;

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
          for (Office office : message.getOffices()){
            System.out.println(office.getName());
          }
        }).match(OfficeAvailability.class, message -> {
          if(message.getOfficeAvaibable()){
            System.out.println(message.getOffice().getName() + " is available.");
          }
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