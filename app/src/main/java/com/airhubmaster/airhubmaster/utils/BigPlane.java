package com.airhubmaster.airhubmaster.utils;

public class BigPlane extends Plane {
    private String name = "Duże";
    private int basicExp = 475;
    private int staffMin = 55;
    private int maxHoursOfFlight = 20;
    private double basicMultiplier = 2.5;

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
