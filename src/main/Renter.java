package main;

import akka.actor.AbstractActor;
import akka.actor.Props;
import messages.AllLocations;

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
          for (Location location : message.getLocations()){
            System.out.println(location.getName());
          }

          //when a renter wants a list of all loctaions
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