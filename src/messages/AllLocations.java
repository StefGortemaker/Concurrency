package messages;

import java.util.ArrayList;
import main.Location;

public class AllLocations {

  private final Location location;

  public AllLocations(Location locations){
    this.location = locations;
  }

  public Location getLocation(){
    return location;
  }
}