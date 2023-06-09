package com.airhubmaster.airhubmaster.dto.api;

/**
 * A helper class responsible for mapping the standard message data from the server to the
 * successful entry of the user's email address change in the main menu
 */
public class ChangeUserEmailResponseDto {

    /**
     * Variable declaration
     */
    String message;
    String newEmail;

    public ChangeUserEmailResponseDto(String message, String newEmail) {
        this.message = message;
        this.newEmail = newEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
