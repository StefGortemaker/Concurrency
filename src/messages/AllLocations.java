package messages;

import java.util.ArrayList;
import main.Location;

public class AllLocations {

  private final ArrayList<Location> locations;

  public AllLocations(ArrayList<Location> locations){
    this.locations = locations;
  }

  public ArrayList<Location> getLocations(){
    return locations;
  }
}
