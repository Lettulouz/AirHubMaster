package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class for mapping personnel data from the server in shop view
 */
public class PersonnelShopDto {

    /**
     * Variable declaration
     */
    int id;
    String fullName;
    String categoryName;
    int experience;
    int cooperation;
    int rebelliousness;
    int skills;
    int price;

    public PersonnelShopDto(int id, String fullName, String categoryName, int experience, int cooperation, int rebelliousness, int skills, int price) {
        this.id = id;
        this.fullName = fullName;
        this.categoryName = categoryName;
        this.experience = experience;
        this.cooperation = cooperation;
        this.rebelliousness = rebelliousness;
        this.skills = skills;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
