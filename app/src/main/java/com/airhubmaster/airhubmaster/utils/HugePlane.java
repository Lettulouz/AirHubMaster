package com.airhubmaster.airhubmaster.utils;

public class HugePlane extends Plane {
    private String name = "Jumbo";
    private int basicExp = 1250;
    private int staffMin = 70;
    private int maxHoursOfFlight = 15;
    private double basicMultiplier = 5.0;

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
