package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class to map the plane bought by the user
 */
public class PlaneBoughtDto {

    /**
     * Variable declaration
     */
    String planeName;
    String categoryName;
    int landingGeer;
    int wings;
    int engine;
    int upgrade;
    boolean isAvailable;

    public PlaneBoughtDto(String planeName, String categoryName, int landingGeer, int wings, int engine, int upgrade, boolean isAvailable) {
        this.planeName = planeName;
        this.categoryName = categoryName;
        this.landingGeer = landingGeer;
        this.wings = wings;
        this.engine = engine;
        this.upgrade = upgrade;
        this.isAvailable = isAvailable;
    }

    public void upgrade() {
        if (upgrade < 5) {
            upgrade++;
        }
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
