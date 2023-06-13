package com.airhubmaster.airhubmaster.utils;

import java.util.Random;

public class Utils {
    public static int generateRandomNumberFromRangeInt(int min, int max){
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    public static double generateRandomNumberFromRangeDouble(double min, double max){
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }
}
