package messages;

import main.Office;

public final class OfficeRented {

  private Boolean officeRented;
  private Office office;

  public OfficeRented(Boolean officeRented, Office office) {
    this.officeRented = officeRented;
    this.office = office;
  }

  public Boolean getOfficeRented() {
    return officeRented;
  }

  public Office getOffice() {
    return office;
  }
}
