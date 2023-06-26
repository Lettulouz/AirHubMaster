package com.airhubmaster.airhubmaster.dto.game;

import java.util.List;

public class SetPersonnelRequestDto {

    /**
     * Variable declaration
     */
    int planeId;
    List<Integer> crew;

    public SetPersonnelRequestDto(int planeId, List<Integer> crew) {
        this.planeId = planeId;
        this.crew = crew;
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
}
