package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's register inputs
 */
public class RegisterRequestDto {

    /**
     * Variable declaration
     */
    String firstName;
    String lastName;
    String login;
    String emailAddress;
    String password;
    String confirmedPassword;

    public RegisterRequestDto(String firstName, String lastName, String login,
                              String emailAddress, String password, String confirmedPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.emailAddress = emailAddress;
        this.password = password;
        this.confirmedPassword = confirmedPassword;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }
}
