package messages;

public class LocationAndOffice {

  private String locationName;
  private int amountOfPeople;

  public LocationAndOffice(String locationName, int amountOfPeople) {
    this.locationName = locationName;
    this.amountOfPeople = amountOfPeople;
  }

  public String getLocationName() {
    return locationName;
  }

  public int getAmountOfPeople() {
    return amountOfPeople;
  }
}
