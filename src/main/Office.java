package main;

import akka.actor.ActorRef;
import java.util.ArrayList;
import messages.ReleaseOffice;

/**
 * this class represents the Office
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class Office {

  private Boolean available;
  private String name;
  private ActorRef currentRenter;
  private ArrayList<ActorRef> waitingRenters;
  private int capacity = (int) (Math.random() * 10) + 1;

  /**
   * Office constructor
   *
   * @param name name of the office
   */
  public Office(String name) {
    available = true;
    this.name = name;
    waitingRenters = new ArrayList<>();
  }

  /**
   *
   * @param placesToRent the amount of people you want to rent the office with
   * @return boolean is the office is available
   */
  Boolean getAvailable(int placesToRent) {
    return available && placesToRent <= capacity;
  }

  /**
   * Adds a Renter to the waiting list
   *
   * @param renter an actor ref of a Renter who want to wait for this office
   */
  void addWaitingRenter(ActorRef renter) {
    waitingRenters.add(renter);
  }

  /**
   * @return returns the name of this office
   */
  String getName() {
    return this.name;
  }

  /**
   * @param currentRenter sets the new current Renter
   */
  void rent(ActorRef currentRenter) {
    available = false;
    this.currentRenter = currentRenter;
  }

  /**
   * Sets the availability to true and sets the current renter back to null the also calls the nextWaitingRenter method
   */
  void release() {
    available = true;
    this.currentRenter = null;
    nextWaitingRenter();
  }

  /**
   * @return returns the current renter
   */
  ActorRef getCurrentRenter() {
    return currentRenter;
  }

  /**
   * if there is a renter waiting on this office he will be notified
   */
  private void nextWaitingRenter() {
    if (!waitingRenters.isEmpty()) {
      waitingRenters.get(0).tell(new ReleaseOffice(this), ActorRef.noSender());
    }
  }
}