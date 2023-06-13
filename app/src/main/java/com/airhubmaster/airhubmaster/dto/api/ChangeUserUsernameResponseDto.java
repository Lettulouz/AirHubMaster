package com.airhubmaster.airhubmaster.dto.api;

/**
 * A helper class responsible for mapping the standard message data from the server to the
 * successful entry of the user's first name and last name change in the main menu
 */
public class ChangeUserUsernameResponseDto {

    /**
     * Variable declaration
     */
    String newFirstName;
    String newLastName;
    String message;

    public ChangeUserUsernameResponseDto(String newFirstName, String newLastName, String message) {
        this.newFirstName = newFirstName;
        this.newLastName = newLastName;
        this.message = message;
    }

    public String getNewFirstName() {
        return newFirstName;
    }

    public void setNewFirstName(String newFirstName) {
        this.newFirstName = newFirstName;
    }

    public String getNewLastName() {
        return newLastName;
    }

    public void setNewLastName(String newLastName) {
        this.newLastName = newLastName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
