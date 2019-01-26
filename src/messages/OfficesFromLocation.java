package messages;

import java.util.ArrayList;
import main.Office;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public class OfficesFromLocation {

  private ArrayList<Office> offices;

  public OfficesFromLocation(ArrayList<Office> offices) {
    this.offices = offices;
  }

  public ArrayList<Office> getOffices() {
    return offices;
  }
}
