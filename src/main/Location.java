package main;

import java.util.ArrayList;

public class Location {

    private ArrayList<Office> offices = new ArrayList<>();
    private String name;

    public Location(String name){
        this.name = name;
        int capacity = (int) (Math.random() * 50) + 10;
        for(int i = 0; i <= capacity; i++){
            offices.add(new Office(name + " office_" + i));
        }
    }

    public Boolean checkAvailabilityOffice(int placesToRent){
        for (Office office : offices){
            if (office.getAvailable(placesToRent)){
                return true;
            }
        }
        return false;
    }

    public ArrayList<Office> getOffices() {
        return offices;
    }

    public String getName(){
        return this.name;
    }
}
