package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

public class Tires extends PlaneService{
    private String name = "Opony";

    public String getName() {
        return name;
    }

    @Override
    public void generateDamage() {
        int damage = generateRandomNumberFromRangeInt(75, 500);
        currentState -= damage;
        if(currentState < 0)
            currentState = 0;
    }
}
