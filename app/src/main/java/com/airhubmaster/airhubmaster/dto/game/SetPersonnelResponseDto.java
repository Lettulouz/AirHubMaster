package com.airhubmaster.airhubmaster.dto.game;

import java.util.List;

public class SetPersonnelResponseDto {

    /**
     * Variable declaration
     */
    List<SetPersonnelPlaneDto> planes;
    ListPersonnelDto workers;

    public SetPersonnelResponseDto(List<SetPersonnelPlaneDto> planes, ListPersonnelDto workers) {
        this.planes = planes;
        this.workers = workers;
    }

    public List<SetPersonnelPlaneDto> getPlanes() {
        return planes;
    }

    public void setPlanes(List<SetPersonnelPlaneDto> planes) {
        this.planes = planes;
    }

    public ListPersonnelDto getWorkers() {
        return workers;
    }

    public void setWorkers(ListPersonnelDto workers) {
        this.workers = workers;
    }
}
