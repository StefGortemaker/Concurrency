package main;

import java.util.ArrayList;

public class Location {

  private ArrayList<Office> offices = new ArrayList<>();
  private String name;

  public Location(String name, String rentAgentName) {
    this.name = name;
    int capacity = (int) (Math.random() * 40) + 10;
    //random.nextInt(max - min + 1) + min
    for (int i = 1; i <= capacity + 1; i++) {
      offices.add(new Office("Office_" + i));
    }
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
    return this.name;
  }

}
