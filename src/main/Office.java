package main;

import akka.actor.ActorRef;
import java.util.ArrayList;
import messages.ReleaseOffice;

public class Office {

  private Boolean available;
  private String name;
  private ActorRef currentRenter;
  private ArrayList<ActorRef> waitingRenters;
  private int capacity = (int) (Math.random() * 10) + 1;

  public Office(String name) {
    available = true;
    this.name = name;
    waitingRenters = new ArrayList<>();
  }

  public Boolean getAvailable(int placesToRent) {
    if (available && placesToRent <= capacity) {
      return true;
    }
    return false;
  }

  public void addWaitingRenter(ActorRef renter){
    waitingRenters.add(renter);
  }

  public ArrayList<ActorRef> getWaitingRenters() {
    return waitingRenters;
  }

  public String getName() {
    return this.name;
  }

  public void rent(ActorRef currentRenter) {
    available = false;
    this.currentRenter = currentRenter;
  }

  public void release() {
    available = true;
    this.currentRenter = null;
    nextWaitingRenter();
  }

  public ActorRef getCurrentRenter() {
    return currentRenter;
  }

  private void nextWaitingRenter(){
    if(!waitingRenters.isEmpty()){
      waitingRenters.get(0).tell(new ReleaseOffice(this), ActorRef.noSender());
    }
  }

}