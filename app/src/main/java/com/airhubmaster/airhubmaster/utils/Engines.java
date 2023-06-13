package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

public class Engines extends PlaneService{
    private String name = "Silniki";

    public String getName() {
        return name;
    }

    @Override
    public void generateDamage() {
        int damage = generateRandomNumberFromRangeInt(20, 100);
        currentState -= damage;
        if(currentState < 0)
            currentState = 0;
    }
}
