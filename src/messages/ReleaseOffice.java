package messages;

import main.Office;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public final class ReleaseOffice {

  private Office office;

  public ReleaseOffice(Office office) {
    this.office = office;
  }

  public Office getOffice() {
    return office;
  }
}
