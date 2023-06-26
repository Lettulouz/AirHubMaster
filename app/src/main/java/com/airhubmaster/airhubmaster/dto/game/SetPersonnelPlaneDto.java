package com.airhubmaster.airhubmaster.dto.game;

public class SetPersonnelPlaneDto {

    /**
     * Variable declaration
     */
    int id;
    String planeName;
    String categoryName;

    public SetPersonnelPlaneDto(int id, String planeName, String categoryName) {
        this.id = id;
        this.planeName = planeName;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
