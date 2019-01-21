package main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import java.util.ArrayList;
import messages.RenterLocations;

public class SuperFlex {

  public ActorSystem actorSystem;
  //public static ArrayList<main.Location> locations;

  public static void main(String[] args) {
    SuperFlex superFlex = new SuperFlex();
    superFlex.run();
  }

  private void run() {
    actorSystem = ActorSystem.create("SuperFlex-App");

    //locations = new ArrayList<>(5);
    //locations.add(new main.Location("Enschede"));
    //locations.add(new main.Location("Deventer"));

    ArrayList<ActorRef> rentAgents = new ArrayList<>();

    for (int i = 0; i < 3; i++){
      rentAgents.add(actorSystem.actorOf(RentAgent.prop("RentAgent_" + i)));
    }

    ActorRef renter = actorSystem.actorOf(Renter.prop("renter"));

    for (ActorRef actorRef : rentAgents){
      actorRef.tell(new RenterLocations(), renter);
    }

    //System.out.println("is there a office available?");
    //rentAgent1.tell(new RenterRentOffice(3), renter);
    //rentAgentEnschede.tell(main.Message.I_WANT_A_LIST_OF_OFFICES, renter);
  }
}
