package com.airhubmaster.airhubmaster.dto.game;

import java.util.ArrayList;

public class PlaneSetRoutDto {

    /**
     * Variable declaration
     */
    int planeId;
    String planeName;
    String planeCategory;
    int level;
    ArrayList<RoutePlaneDto> routes;
    ArrayList<Integer> crew;

    public PlaneSetRoutDto(int planeId, String planeName, String planeCategory, int level, ArrayList<RoutePlaneDto> routes, ArrayList<Integer> crew) {
        this.planeId = planeId;
        this.planeName = planeName;
        this.planeCategory = planeCategory;
        this.level = level;
        this.routes = routes;
        this.crew = crew;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getPlaneCategory() {
        return planeCategory;
    }

    public void setPlaneCategory(String planeCategory) {
        this.planeCategory = planeCategory;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<RoutePlaneDto> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<RoutePlaneDto> routes) {
        this.routes = routes;
    }

    public ArrayList<Integer> getCrew() {
        return crew;
    }

    public void setCrew(ArrayList<Integer> crew) {
        this.crew = crew;
    }
}
