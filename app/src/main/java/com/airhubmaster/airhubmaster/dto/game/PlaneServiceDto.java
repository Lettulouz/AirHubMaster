package com.airhubmaster.airhubmaster.dto.game;

public class PlaneServiceDto {

    /**
     * Variable declaration
     */
    String name;
    String categoryName;
    int engine;
    int landingGeer;
    int wings;

    public PlaneServiceDto(String name, String categoryName, int engine, int landingGeer, int wings) {
        this.name = name;
        this.categoryName = categoryName;
        this.engine = engine;
        this.landingGeer = landingGeer;
        this.wings = wings;
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

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getLandingGeer() {
        return landingGeer;
    }

    public void setLandingGeer(int landingGeer) {
        this.landingGeer = landingGeer;
    }

    public int getWings() {
        return wings;
    }

    public void setWings(int wings) {
        this.wings = wings;
    }
}
