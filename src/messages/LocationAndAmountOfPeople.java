package messages;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public final class LocationAndAmountOfPeople {

  private String locationName;
  private int amountOfPeople;

  public LocationAndAmountOfPeople(String locationName, int amountOfPeople) {
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
