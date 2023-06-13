package com.airhubmaster.airhubmaster.utils;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private int level;
    private int exp;
    private int money;
    private List<Plane> planes = new ArrayList<>();

    private UserLevel userLevel = new UserLevel();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public boolean addExp(int exp) {
        this.exp += exp;
        return checkForLevelUp();
    }

    public void setPlanes(List<Plane> planes){
        this.planes = planes;
    }

    public List<Plane> getPlanes(){
        return planes;
    }

    public void addPlane(Plane plane){
        planes.add(plane);
    }

    public boolean checkForLevelUp(){
        if (level == userLevel.getMaxLevel()){
            return false;
        }
        if(userLevel.getExpAtLevel(level+1) < exp){
            level++;
            return true;
        }
        return false;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void addMoney(int money) {
        this.money += money;
    }

    public boolean takeMoney(int money) {
        if(money > this.money)
            return false;
        else
            this.money -= money;
        return true;
    }

    public UserLevel getUserLevel(){
        return userLevel;
    }
}
