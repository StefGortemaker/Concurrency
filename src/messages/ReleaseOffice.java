package messages;

import main.Office;

public final class ReleaseOffice {

  private Office office;

  public ReleaseOffice(Office office) {
    this.office = office;
  }

  public Office getOffice() {
    return office;
  }
}
