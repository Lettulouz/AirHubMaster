package com.airhubmaster.airhubmaster.dto.game;

public class SetPersonnelWorkerDto {

    /**
     * Variable declaration
     */
    int id;
    String fullName;
    boolean available;
    int experience;
    int cooperation;
    int rebelliousness;
    int skills;

    public SetPersonnelWorkerDto(int id, String fullName, boolean available, int experience, int cooperation, int rebelliousness, int skills) {
        this.id = id;
        this.fullName = fullName;
        this.available = available;
        this.experience = experience;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
        this.skills = skills;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
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

    public void setRebelliousness(int rebelliousness) {
        this.rebelliousness = rebelliousness;
    }

    public int getSkills() {
        return skills;
    }

    public void setSkills(int skills) {
        this.skills = skills;
    }
}
