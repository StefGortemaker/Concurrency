import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import java.util.Scanner;

public class Renter extends AbstractActor {

  private ActorRef rentAgent;

  private Renter(ActorRef rentAgent) {
    this.rentAgent = rentAgent;
  }

  public static Props prop(ActorRef rentAgent) {
    return Props.create(Renter.class, rentAgent);
  }

  @Override
  public Receive createReceive() {
    return receiveBuilder().match(Message.class, message -> {

      Scanner scanner = new Scanner(System.in);

      switch (message) {
        case OFFICE_AVAILABLE:
          System.out.println("Office is available do you  want ro rent or not?(Y/N)");

          if (scanner.nextLine().equalsIgnoreCase("Y")) {
            getSender().tell(Message.I_WANT_TO_RENT_OFFICE, getSelf());
          } else {
            getSender().tell(Message.I_DONT_WANT_TO_RENT_OFFICE, getSelf());
          }
          break;
        case OFFICE_NOT_AVAILABLE:
          System.out.println("Office is not available do you want to be notified when it is?");

          if (scanner.nextLine().equalsIgnoreCase("Y")) {
            getSender().tell(Message.I_WANT_TO_RESERVE, getSelf());
          } else {
            getSender().tell(Message.I_DONT_WANT_TO_RESERVE, getSelf());
          }
          break;
        case HERE_IS_A_LIST_OF_LOCATIONS:
          System.out.println("Great, all the locations");
          break;
        case HERE_IS_A_LIST_OF_OFFICES:
          System.out.println("Great, all the offices");
          break;
        default:
          System.out.println("Renter Default");
      }
    }).match(String.class, System.out::println).build();
  }

  @Override
  public void preStart() {
    System.out.println("Renter started");
  }

  public void postStop() {
    System.out.println("Renter exiting");
  }
}
