package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeDouble;
import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

public class Flight {
    private int prize;
    private int flightLength;
    private int exp;

    private Plane plane;
    private UserProfile userProfile;

    public Flight(Plane plane, UserProfile userProfile){
        this.plane = plane;
        this.userProfile = userProfile;
    }

    public void generateFlight(){
        generateFlightLength();
        generatePrize();
        generateExp();
    }

    private void generateFlightLength(){
        int maxFlightLength = plane.getMaxHoursOfFlight();
        this.flightLength = generateRandomNumberFromRangeInt(1, maxFlightLength);
    }

    private void generatePrize(){
        double basicMultiplier = plane.getBasicPrizeMultiplier();
        double expBoost = userProfile.getUserLevel().getExpBoostAtLevel(userProfile.getLevel());
        double additionalPrizeMultiplier = generateRandomNumberFromRangeDouble(0.6, 1.4);
        this.prize = (int)(flightLength * basicMultiplier * additionalPrizeMultiplier * expBoost);
    }

    private void generateExp(){
        double basicMultiplier = plane.getBasicPrizeMultiplier();
        double expBoost = userProfile.getUserLevel().getExpBoostAtLevel(userProfile.getLevel());
        double additionalExpMultiplier = generateRandomNumberFromRangeDouble(0.75, 1.25);
        this.exp = (int)(flightLength * expBoost * additionalExpMultiplier * basicMultiplier);
    }

}
