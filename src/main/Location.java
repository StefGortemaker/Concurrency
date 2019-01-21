package main;

import java.util.ArrayList;

public class Location {

  private ArrayList<Office> offices = new ArrayList<>();
  private String name;
  private String rentAgentName;

  public Location(String name, String rentAgentName) {
    this.name = name;
    int capacity = (int) (Math.random() * 50) + 10;
    for (int i = 0; i <= capacity; i++) {
      offices.add(new Office(name + " office_" + i));
    }
    this.rentAgentName = rentAgentName;
  }

  public Boolean checkAvailabilityOffice(int placesToRent) {
    for (Office office : offices) {
      if (office.getAvailable(placesToRent)) {
        return true;
      }
    }
    return false;
  }

  public ArrayList<Office> getOffices() {
    return offices;
  }

  public String getName() {
    return this.rentAgentName + " " + this.name;
  }
}
