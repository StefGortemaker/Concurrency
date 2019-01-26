package messages;

import main.Location;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class AllLocations {

  private final Location location;

  public AllLocations(Location locations) {
    this.location = locations;
  }

  public Location getLocation() {
    return location;
  }
}