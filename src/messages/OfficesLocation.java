package messages;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public final class OfficesLocation {

  private String locationName;

  public OfficesLocation(String locationName) {
    this.locationName = locationName;
  }

  public String getLocationName() {
    return locationName;
  }
}
