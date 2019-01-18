public class Office {

    private Boolean available;
    private String name;
    int count = 1;

    public Office(String name){
        available = true;
        this.name = name + count;
        //count++;
    }

    public Boolean getAvailable() {
        return available;
    }

    public String getName(){
        return this.name;
    }

}
