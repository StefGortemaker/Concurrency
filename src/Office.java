import java.util.ArrayList;

public class Office {

  private Boolean available;
  private String name;
  private static int nextCount = 1;
  private ArrayList<FlexibleWorkplace> flexibleWorkplaces;

  public Office(String name) {
    flexibleWorkplaces = new ArrayList<>();
    int count = nextCount;
    nextCount++;
    available = true;
    int maxWerkplekken = (int) (Math.random() * 10) + 1;
    this.name = name + count;
    for (int i = 1; i <= maxWerkplekken; i++) {
      flexibleWorkplaces.add(new FlexibleWorkplace("Flexible workplace " + i));
    }
  }

  public Boolean getAvailable() {
    return available;
  }

  public String getName() {
    return this.name;
  }
}
