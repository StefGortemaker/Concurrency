package messages;

import main.Office;

public final class WaitForOffice {

  private Boolean iWantToWaitForOffice;
  private Office office;

  public WaitForOffice(Boolean iWantToWaitForOffice, Office office) {
    this.iWantToWaitForOffice = iWantToWaitForOffice;
    this.office = office;
  }

  public Boolean getiWantToWaitForOffice() {
    return iWantToWaitForOffice;
  }

  public Office getOffice() {
    return office;
  }
}
