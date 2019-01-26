package messages;

import main.Office;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public final class OfficeAvailability {

  private Boolean officeAvaibable;
  private Office office;

  public OfficeAvailability(Boolean officeAvaibable, Office office) {
    this.officeAvaibable = officeAvaibable;
    this.office = office;
  }

  public Boolean getOfficeAvaibable() {
    return officeAvaibable;
  }

  public Office getOffice() {
    return office;
  }
}
