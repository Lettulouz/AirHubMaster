package com.airhubmaster.airhubmaster.dto;

public class Plane {
    private String name;
    private int upgradeLevel;

    public Plane(String name, int upgradeLevel) {
        this.name = name;
        this.upgradeLevel = upgradeLevel;
    }

    public void upgrade() {
        if (upgradeLevel < 5) {
            upgradeLevel++;
        }
    }

    public String getName;
    public String getName() {
        return name;
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