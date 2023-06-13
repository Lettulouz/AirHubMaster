package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

public class Wings extends PlaneService{
    private String name = "Skrzydła";

    public String getName() {
        return name;
    }

    @Override
    public void generateDamage() {
        int damage = generateRandomNumberFromRangeInt(1, 75);
        currentState -= damage;
        if(currentState < 0)
            currentState = 0;
    }
}
