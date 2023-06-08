package com.airhubmaster.airhubmaster.utils;

public class StaffStats {
    private int experience;
    private int skills;
    private int cooperation;
    private int rebelliousness;

    public int getExpirience() {
        return experience;
    }

    public void setExpirience(int expirience) {
        this.experience = expirience;
    }

    public int getSkills() {
        return skills;
    }

    public void setSkills(int skills) {
        this.skills = skills;
    }

    public int getCooperation() {
        return cooperation;
    }

    public void setCooperation(int cooperation) {
        this.cooperation = cooperation;
    }

    public int getRebelliousness() {
        return rebelliousness;
    }

    public void setRebelliousness(int rebelliousness, int maxValue) {
        this.rebelliousness = maxValue - rebelliousness;
    }


}
