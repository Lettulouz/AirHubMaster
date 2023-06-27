package com.airhubmaster.airhubmaster.dto.game;

import java.util.ArrayList;

public class RoutePlaneResponseDto {

    /**
     * Variable declaration
     */
    ArrayList<String> planesCategories;
    ArrayList<PlaneSetRoutDto> planes;

    public RoutePlaneResponseDto(ArrayList<String> planesCategories, ArrayList<PlaneSetRoutDto> planes) {
        this.planesCategories = planesCategories;
        this.planes = planes;
    }

    public ArrayList<String> getPlanesCategories() {
        return planesCategories;
    }

    public void setPlanesCategories(ArrayList<String> planesCategories) {
        this.planesCategories = planesCategories;
    }

    public ArrayList<PlaneSetRoutDto> getPlanes() {
        return planes;
    }

    public void setPlanes(ArrayList<PlaneSetRoutDto> planes) {
        this.planes = planes;
    }
}
