package com.airhubmaster.airhubmaster.dto.game;

public class RoutePlaneDto {

    /**
     * Variable declaration
     */
    int id;
    int hours;

    public RoutePlaneDto(int id, int hours) {
        this.id = id;
        this.hours = hours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }
}
