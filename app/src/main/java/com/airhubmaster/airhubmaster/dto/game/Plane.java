package com.airhubmaster.airhubmaster.dto.game;

public class Plane {

    private String name;
    private String category;
    private int upgradeLevel;

    public Plane(String name, String category, int upgradeLevel) {
        this.category = category;
        this.name = name;
        this.upgradeLevel = upgradeLevel;
    }

    public void upgrade() {
        if (upgradeLevel < 5) {
            upgradeLevel++;
        }
    }

    // Poprawione deklaracje poniżej
    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
}