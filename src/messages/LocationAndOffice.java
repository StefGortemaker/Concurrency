package messages;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class LocationAndOffice {

  private String locationName;
  private String officeName;
  private int amountOfPeople;

  public LocationAndOffice(String locationName, String officeName, int amountOfPeople) {
    this.locationName = locationName;
    this.officeName = officeName;
    this.amountOfPeople = amountOfPeople;
  }

  public String getLocationName() {
    return locationName;
  }

  public String getOfficeName() {
    return officeName;
  }

  public int getAmountOfPeople() {
    return amountOfPeople;
  }
}
