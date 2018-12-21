import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class Renter extends AbstractActor {

    private ActorRef rentAgent;

    private Renter(ActorRef rentAgent){
        this.rentAgent = rentAgent;
    }

    public static Props prop(ActorRef rentAgent){
        return Props.create(Renter.class, rentAgent);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder().match(Message.class, message -> {
            switch (message) {
                case I_WANT_TO_RENT_OFFICE:
                    System.out.println("I want to rent a office");
                    break;
                case OFFICE_AVAILABLE:
                    System.out.println("Great");
                    break;
                case OFFICE_NOT_AVAILABLE:
                    System.out.println("To bad");
                    break;
                default:
                    System.out.println("Hi");
            }
        }).match(String.class, message -> {
            System.out.println(message);
        }).build();


    }

    @Override
    public void preStart() {
        System.out.println("Renter started");
        rentAgent.tell(Message.I_WANT_TO_RENT_OFFICE, getSelf());

    }

    public void postStop() {
        System.out.println("Renter exiting");
    }
}
