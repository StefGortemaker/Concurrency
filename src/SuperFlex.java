import akka.actor.ActorRef;
import akka.actor.ActorSystem;

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

        ActorRef rentAgentEnschede = locationCheck("Enschede").getRentAgent();
        ActorRef rentAgentDeventer = locationCheck("Deventer").getRentAgent();

        ActorRef renter = actorSystem.actorOf(Renter.prop(rentAgentEnschede), "renter");

        renter.tell(Message.I_WANT_TO_RENT_OFFICE, rentAgentEnschede);
        if (locations.get(0).checkAvailabilityOffice()) {
            rentAgentEnschede.tell(Message.OFFICE_AVAILABLE, renter);
        } else {
            rentAgentEnschede.tell(Message.OFFICE_NOT_AVAILABLE, renter);
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
