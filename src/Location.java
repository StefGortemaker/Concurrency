import akka.actor.ActorRef;
import akka.actor.Props;

import java.util.ArrayList;

public class Location {

    private ArrayList<Office> offices = new ArrayList<>(10);;
    private ActorRef rentAgent;
    private String name;

    public Location(String name){
        this.name = name;
        for(int i = 0; i < offices.size(); i++){
            offices.add(new Office("office"));
            System.out.println(offices.get(i).getName());
        }
        rentAgent = SuperFlex.actorSystem.actorOf(Props.create(RentAgent.class), "rentAgent"+name);
    }

    public ActorRef getRentAgent() {
        return rentAgent;
    }

    public Boolean checkAvailabilityOffice(){
        boolean availability = false;
        for(Office office : offices){
            System.out.println(office.getName());
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
