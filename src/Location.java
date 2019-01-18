import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.ArrayList;

public class Location {

    private ArrayList<Office> offices = new ArrayList<>();
    private int capacity;
    private ActorRef rentAgent;
    private String name;
    private static int nextCount = 1;

    public Location(String name){
        this.name = name;
        this.capacity = (int) (Math.random()*50) +10;
        int count = nextCount;
        for(int i = 0; i <= capacity; i++){
            offices.add(new Office("office "));
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

    public ArrayList<Office> getOffices() {
        return offices;
    }

    public String getName(){
        return this.name;
    }
}
