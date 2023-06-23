package com.airhubmaster.airhubmaster.gameAlgorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Staff {
    public static int drawOneStat(List<Integer> scales) {
        int[] range = {1, 20, 40, 60, 80, 100};

        int sumOfScales = 0;
        for (int scale : scales) {
            sumOfScales += scale;
        }

        Random rand = new Random();
        int randomScale = rand.nextInt(sumOfScales);

        int i = 0;
        while (i < range.length - 1 && randomScale >= scales.get(i)) {
            randomScale-= scales.get(i);
            i++;
        }

        int lowerLimit = range[i];
        int upperLimit;
        if(i<5)
            upperLimit = range[i + 1];
        else
            upperLimit = range[i];

        double randomNumber = rand.nextDouble();

        int drawNumber = (int) (lowerLimit + randomNumber * (upperLimit - lowerLimit));

        if(drawNumber==100){
            int randNumHun = rand.nextInt(15);
            if(randNumHun != 1){
                return drawOneStat(scales);
            }
        }

        return drawNumber;
    }
}
