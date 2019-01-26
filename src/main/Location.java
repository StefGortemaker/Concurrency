package main;

import java.util.ArrayList;

/**
 * The location class
 * Has a list of offices and a name.
 *
 * @Author Vincent Witten & Stef Gortemaker
 */

public class Location {

  private ArrayList<Office> offices = new ArrayList<>();
  private String name;

  /**
   * Constructor of a location
   *
   * @param name of the location
   */
  public Location(String name) {
    this.name = name;
    int capacity = (int) (Math.random() * 40) + 10;
    for (int i = 1; i <= capacity + 1; i++) {
      offices.add(new Office("Office_" + i));
    }
  }

  /**
   * Checks the availability of an office
   *
   * @param placesToRent amount of capacity is must have
   * @return true if available, false if not
   */
  public Boolean checkAvailabilityOffice(int placesToRent) {
    for (Office office : offices) {
      if (office.getAvailable(placesToRent)) {
        return true;
      }
    }
    return false;
  }

  //Getters
  public ArrayList<Office> getOffices() {
    return offices;
  }

  public String getName() {
    return this.name;
  }

}
