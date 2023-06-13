package com.airhubmaster.airhubmaster.utils;

public abstract class PlaneService {
    private final int maxState = 10000;
    protected int currentState;

    public int getCurrentState() {
        return currentState;
    }

    public void repairPlane(){
        currentState = maxState;
    }

    public abstract void generateDamage();
}
