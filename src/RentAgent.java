import akka.actor.AbstractActor;

public class RentAgent extends AbstractActor {

//    private String name;
//    private Location location;
//
//    public RentAgent(String name, Location location){
//        this.name = name;
//        this.location = location;
//    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, message -> {
            switch (message) {
                case RENTER_WANT_A_OFFICE:
                    System.out.println("Alright, let me check if it is available");

//                    if(location.checkAvalabilityOffice()) {
//                        getSender().tell(Message.OFFICE_AVAILABLE, getSelf());
//                    }else{
//                        getSender().tell(Message.OFFICE_NOT_AVAILABLE, getSelf());
//                        System.out.println("Office is not available");
//                    }
                    break;
                case OFFICE_NOT_AVAILABLE:
                    System.out.println("Office is not available");
                    break;
                case OFFICE_AVAILABLE:
                    System.out.println("There is a office available");
                default:
                    System.out.println("Hi");
            }
        }).match(String.class, message -> {
            System.out.println(message);
        }).build();
    }

    @Override
    public void preStart() {
        System.out.println("RentAgent started");
    }

    public void postStop() {
        System.out.println("RentAgent exiting");
    }
}
