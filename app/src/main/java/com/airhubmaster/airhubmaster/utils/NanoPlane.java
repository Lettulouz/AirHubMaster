package com.airhubmaster.airhubmaster.utils;

public class NanoPlane extends Plane {
    private String name = "Mikro";
    private int basicExp = 2;
    private int staffMin = 0;
    private int maxHoursOfFlight = 4;
    private double basicMultiplier = 1.0;

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
