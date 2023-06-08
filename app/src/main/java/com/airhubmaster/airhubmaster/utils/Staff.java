package com.airhubmaster.airhubmaster.utils;

import static com.airhubmaster.airhubmaster.gameAlgorithms.Staff.drawOneStat;

import java.util.ArrayList;
import java.util.List;

public class Staff {
    protected String name;
    protected int maxSingle;
    protected StaffStats person;

    private final int planeMin;
    private final int userLevel;

    public Staff(int userLevel, int planeMin){
        this.userLevel = userLevel;
        this.planeMin = planeMin;
    }


    public void generateStaff(){
        person.setCooperation(drawOneStat());
        person.setExpirience(drawOneStat());
        person.setSkills(drawOneStat());
        person.setRebelliousness(drawOneStat(), maxSingle);
    }

}

