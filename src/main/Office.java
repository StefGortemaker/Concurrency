package main;

public class Office {

  private Boolean available;
  private String name;
  private int capacity = (int) (Math.random() * 10) + 1;

  public Office(String name) {
    available = true;
    this.name = name;
  }

  public Boolean getAvailable(int placesToRent) {
    if (available && placesToRent <= capacity){
      return true;
    }
    return false;
  }

  public String getName() {
    return this.name;
  }

  public void rent(){
    available = false;
  }
}
