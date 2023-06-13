package com.airhubmaster.airhubmaster.dto.api;

/**
 * A helper class responsible for mapping the standard message data from the server to the
 * successful entry of the user's password change in the main menu
 */
public class ChangeUserPasswordResponseDto {

    /**
     * Variable declaration
     */
    String message;

    public ChangeUserPasswordResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
