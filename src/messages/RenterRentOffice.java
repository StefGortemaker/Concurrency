package messages;

/**
 * This class is a Message
 *
 * @author Vincent Witten, Stef Gortemaker
 */
public final class RenterRentOffice {

  private final int placesToRent;

  public RenterRentOffice(int placesToRent) {
    this.placesToRent = placesToRent;
  }

  public int getPlacesToRent() {
    return placesToRent;
  }
}