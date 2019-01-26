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

  Boolean getAvailable(int placesToRent) {
    return available && placesToRent <= capacity;
  }

  void addWaitingRenter(ActorRef renter){
    waitingRenters.add(renter);
  }

  public ArrayList<ActorRef> getWaitingRenters() {
    return waitingRenters;
  }

  String getName() {
    return this.name;
  }

  void rent(ActorRef currentRenter) {
    available = false;
    this.currentRenter = currentRenter;
  }

  void release() {
    available = true;
    this.currentRenter = null;
    nextWaitingRenter();
  }

  ActorRef getCurrentRenter() {
    return currentRenter;
  }

  private void nextWaitingRenter(){
    if(!waitingRenters.isEmpty()){
      waitingRenters.get(0).tell(new ReleaseOffice(this), ActorRef.noSender());
    }
  }
}