package com.airhubmaster.airhubmaster.dto.game;

/**
 * A simple class to map category data
 */
public class CategoryDto {

    /**
     * Variable declaration
     */
    private String name;

    public CategoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
