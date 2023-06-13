package com.airhubmaster.airhubmaster.utils;

public class StandardPlane extends Plane {
    private String name = "Standard";
    private int basicExp = 125;
    private int staffMin = 30;
    private int maxHoursOfFlight = 7;
    private double basicMultiplier = 1.8;

    public String getName() {
        return name;
    }

    @Override
    public int getBasicExp() {
        return basicExp;
    }

    @Override
    public int getStaffMin() {
        return staffMin;
    }

    @Override
    public int getMaxHoursOfFlight() {
        return maxHoursOfFlight;
    }

    @Override
    public double getBasicPrizeMultiplier() {
        return basicMultiplier;
    }
}
