package messages;

import java.util.ArrayList;
import main.Office;

public class OfficesFromLocation {

  private ArrayList<Office> offices;

  public OfficesFromLocation(ArrayList<Office> offices) {
    this.offices = offices;
  }

  public ArrayList<Office> getOffices() {
    return offices;
  }
}
