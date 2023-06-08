package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class to map personnel data
 */
public class PersonnelDto {

    /**
     * Variable declaration
     */
    private String name;
    private String category;
    private int experience;
    private int skills;
    private int cooperation;
    private int rebelliousness;

    public PersonnelDto(String name, String category, int experience, int skills, int cooperation, int rebelliousness) {
        this.name = name;
        this.category = category;
        this.experience = experience;
        this.skills = skills;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getExperience() {
        return experience;
    }

    public int getSkills() {
        return skills;
    }

    public int getCooperation() {
        return cooperation;
    }

    public int getRebelliousness() {
        return rebelliousness;
    }
}
