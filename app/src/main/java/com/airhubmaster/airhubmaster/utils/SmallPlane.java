package com.airhubmaster.airhubmaster.utils;

public class SmallPlane extends Plane {
    private String name = "Mały";
    private int basicExp = 15;
    private int staffMin = 15;
    private int maxHoursOfFlight = 5;
    private double basicMultiplier = 1.3;

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
