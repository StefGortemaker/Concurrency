import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import akka.actor.Props;
import java.util.ArrayList;

public class SuperFlex {

  public ActorSystem actorSystem;
  private ArrayList<Location> locations;

  public static void main(String[] args) {
    SuperFlex superFlex = new SuperFlex();
    superFlex.run();
  }

  private void run() {
    actorSystem = ActorSystem.create("SuperFlex-App");

    locations = new ArrayList<>(5);
    locations.add(new Location("Enschede", actorSystem));
    locations.add(new Location("Deventer", actorSystem));

    ActorRef rentAgentEnschede = actorSystem.actorOf(RentAgent.prop("Enschede", locations.get(0)));
    ActorRef rentAgentDeventer = actorSystem.actorOf(RentAgent.prop("Deventer", locations.get(1)));

    ActorRef renter = actorSystem.actorOf(Renter.prop(rentAgentEnschede), "renter");

    System.out.println("is there a office available?");
    rentAgentEnschede.tell(Message.RENTER_WANT_A_OFFICE, renter);
  }

  private Location locationCheck(String name) {
    for (Location location : locations) {
      if (location.getName().equals(name)) {
        return location;
      }
    }
    return null;
  }
}
