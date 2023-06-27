package com.airhubmaster.airhubmaster.dto.game;

import java.util.ArrayList;

/**
 * Variable declaration
 */
public class PlaneArrivalDto {

     String name;
     String categoryName;
     String arrival;
     ArrayList<PersonnelBoughtDto> workers;

    public PlaneArrivalDto(String name, String categoryName, String arrival, ArrayList<PersonnelBoughtDto> workers) {
        this.name = name;
        this.categoryName = categoryName;
        this.arrival = arrival;
        this.workers = workers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public ArrayList<PersonnelBoughtDto> getWorkers() {
        return workers;
    }

    public void setWorkers(ArrayList<PersonnelBoughtDto> workers) {
        this.workers = workers;
    }
}
