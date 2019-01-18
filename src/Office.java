public class Office {

    private Boolean available;
    private String name;
    private String location;
    private int count;
    private static int nextCount = 1;

    public Office(String name, String location){
        count = nextCount;
        nextCount++;
        available = true;
        this.name = name + count;
        this.location = location;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getName(){
        return this.name;
    }

    public String getLocation() {
        return location;
    }
}
