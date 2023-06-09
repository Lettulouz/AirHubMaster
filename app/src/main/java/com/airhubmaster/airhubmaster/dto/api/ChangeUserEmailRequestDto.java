package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's change email input in the main menu
 */
public class ChangeUserEmailRequestDto {

    /**
     * Variable declaration
     */
    String newEmail;

    public ChangeUserEmailRequestDto(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
