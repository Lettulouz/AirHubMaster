package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's change first name
 * and last name input in the main menu
 */
public class ChangeUserUsernameRequestDto {

    /**
     * Variable declaration
     */
    String firstName;
    String lastName;

    public ChangeUserUsernameRequestDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
