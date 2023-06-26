package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class to map the personnel employed by the user
 */
public class PersonnelBoughtDto {

    /**
     * Variable declaration
     */
    String fullName;
    String categoryName;
    int experience;
    int cooperation;
    int rebelliousness;
    int skills;
    boolean isAvailable;

    public PersonnelBoughtDto(String fullName, String categoryName, int experience, int cooperation, int rebelliousness, int skills, boolean isAvailable) {
        this.fullName = fullName;
        this.categoryName = categoryName;
        this.experience = experience;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
        this.skills = skills;
        this.isAvailable = isAvailable;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }
}
