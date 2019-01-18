import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import java.util.ArrayList;
import scala.sys.Prop;

public class RentAgent extends AbstractActor {

    private String name;
    private Location location;

    private RentAgent(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public static Props prop(String name, Location location){
        return Props.create(RentAgent.class, name, location);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, message -> {
            switch (message) {
                case RENTER_WANT_A_OFFICE:
                    System.out.println("Alright, let me check if it is available");

                    if (location.checkAvailabilityOffice()){
                        getSender().tell(Message.OFFICE_AVAILABLE, getSelf());
                    } else {
                        getSender().tell(Message.OFFICE_NOT_AVAILABLE, getSelf());
                    }
                    break;
                case I_WANT_TO_RENT_OFFICE:
                    System.out.println("Office rented");
                    break;
                case I_DONT_WANT_TO_RENT_OFFICE:
                    System.out.println("Office not rented");
                    break;
                case I_WANT_TO_RESERVE:
                    System.out.println("Office is reserved");
                    break;
                case I_DONT_WANT_TO_RESERVE:
                    System.out.println("Office is not reserved");
                    break;
                default:
                    System.out.println("RentAgent Default");
            }
        }).match(String.class, System.out::println).build();
    }

    @Override
    public void preStart() {
        System.out.println("RentAgent started");
    }

    public void postStop() {
        System.out.println("RentAgent exiting");
    }

    public ArrayList<Office> getOffices(String locationName){
        for(Location location : SuperFlex.locations){
            if(location.getName().equals(locationName)){
                return location.getOffices();
            }
        }
        return null;
    }
}
