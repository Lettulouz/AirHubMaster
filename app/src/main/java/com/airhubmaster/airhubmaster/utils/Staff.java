package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.gameAlgorithms.Staff.drawOneStat;
import static com.airhubmaster.airhubmaster.utils.Utils.generateRandomNumberFromRangeInt;

import java.util.ArrayList;
import java.util.List;

public class Staff {
    protected String name;
    protected int maxSingle;
    protected StaffStats person;
    private int employmentCost;
    private int salary;

    public void generateStaff(int level){
        UserLevel userLevel = new UserLevel();
        person.setCooperation(drawOneStat(userLevel.getSkillsAtLevelMap(level)));
        person.setExpirience(drawOneStat(userLevel.getSkillsAtLevelMap(level)));
        person.setSkills(drawOneStat(userLevel.getSkillsAtLevelMap(level)));
        person.setRebelliousness(drawOneStat(userLevel.getSkillsAtLevelMap(level)), maxSingle);
    }

    public int getEmploymentCost() {
        return employmentCost;
    }

    public void setEmploymentCost(int amountOfPoints) {
        this.employmentCost = amountOfPoints * 20;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int amountOfPoints) {
        this.salary = generateRandomNumberFromRangeInt((amountOfPoints/10),amountOfPoints);
    }

}

