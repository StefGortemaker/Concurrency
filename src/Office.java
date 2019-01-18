import java.util.Random;

public class Office {

    private Boolean available;
    private String name;
    private int maxWerkplekken;
    private int count = 0;

    public Office(String name){
        available = false;
        Random r = new Random();
        this.maxWerkplekken = r.nextInt((10 - 1) + 1) + 1;
        this.name = name + count;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getName(){
        return this.name;
    }
}
