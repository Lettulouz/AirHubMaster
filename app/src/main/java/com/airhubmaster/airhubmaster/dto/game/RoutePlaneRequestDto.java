package com.airhubmaster.airhubmaster.dto.game;

import java.util.List;

public class RoutePlaneRequestDto {

    /**
     * Variable declaration
     */
    int planeId;
    List<Integer> crew;
    int routeId;

    public RoutePlaneRequestDto(int planeId, List<Integer> crew, int routeId) {
        this.planeId = planeId;
        this.crew = crew;
        this.routeId = routeId;
    }

    public int getPlaneId() {
        return planeId;
    }

    public void setPlaneId(int planeId) {
        this.planeId = planeId;
    }

    public List<Integer> getCrew() {
        return crew;
    }

    public void setCrew(List<Integer> crew) {
        this.crew = crew;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
