package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class for mapping aircraft data from the server in shop view
 */
public class PlaneShopDto {

    /**
     * Variable declaration
     */
    int id;
    String planeName;
    String categoryName;
    int landingGeer;
    int wings;
    int engine;
    int upgrade;
    int price;

    public PlaneShopDto(int id, String planeName, String categoryName, int landingGeer, int wings, int engine, int upgrade, int price) {
        this.id = id;
        this.planeName = planeName;
        this.categoryName = categoryName;
        this.landingGeer = landingGeer;
        this.wings = wings;
        this.engine = engine;
        this.upgrade = upgrade;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public int getEngine() {
        return engine;
    }

    public void setEngine(int engine) {
        this.engine = engine;
    }

    public int getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(int upgrade) {
        this.upgrade = upgrade;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
