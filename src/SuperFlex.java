import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.ArrayList;

public class SuperFlex {

    private ArrayList<Location> locations;

    public static void main(String[] args) {
        SuperFlex superFlex = new SuperFlex();
        superFlex.run();

    }

    private void run() {
        ActorSystem actorSystem = ActorSystem.create("SuperFlex-App");

        locations = new ArrayList<>(5);
        locations.add(new Location("Enschede", actorSystem));
        locations.add(new Location("Deventer", actorSystem));

        ActorRef rentAgentEnschede = locationCheck(locations.get(0).getName()).getRentAgent();
        ActorRef rentAgentDeventer = locationCheck(locations.get(1).getName()).getRentAgent();

        ActorRef renter = actorSystem.actorOf(Renter.prop(rentAgentEnschede), "renter");

        renter.tell(Message.I_WANT_TO_RENT_OFFICE, rentAgentEnschede);
        rentAgentEnschede.tell(Message.RENTER_WANT_A_OFFICE, rentAgentDeventer);
        if (locations.get(0).checkAvailabilityOffice()) {
            renter.tell(Message.OFFICE_AVAILABLE, rentAgentEnschede);
        } else {
            renter.tell(Message.OFFICE_NOT_AVAILABLE, rentAgentEnschede);
        }

        //rentAgent.tell(Message.OFFICE_AVAILABLE, renter);
        //renter.tell(Message.OFFICE_AVAILABLE, rentAgent);
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
