package main;

public class FlexibleWorkplace {

  private String name;
  private Boolean available;

  public FlexibleWorkplace(String name) {
    this.name = name;
    available = true;
  }

  public String getName() {
    return name;
  }

  public Boolean getAvailable() {
    return available;
  }
}
