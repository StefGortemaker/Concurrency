import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.ArrayList;

public class Location {

    private ArrayList<Office> offices = new ArrayList<>();
    private int capacity;
    private ActorRef rentAgent;
    private String name;

    public Location(String name, ActorSystem actorSystem){
        this.name = name;
        this.capacity = 10;
        for(int i = 0; i < capacity; i++){
            offices.add(new Office("office"));
        }
        //rentAgent = actorSystem.actorOf(Props.create(RentAgent.class), "rentAgent"+name);
    }

    public ActorRef getRentAgent() {
        return rentAgent;
    }

    public Boolean checkAvailabilityOffice(){
        boolean availability = false;
        for(Office office : offices){
            //System.out.println(office.getName());
            if(office.getAvailable()) {
                availability = true;
            }
        }
        return availability;
    }

    public String getName(){
        return this.name;
    }
}
