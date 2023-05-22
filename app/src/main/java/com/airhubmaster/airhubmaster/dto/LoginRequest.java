package com.airhubmaster.airhubmaster.dto;

/**
 * The helper class responsible for storing the entered data from the user's login view
 */
public class LoginRequest {

    /**
     * Variable declaration
     */
    String loginOrEmail;
    String password;

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }
}
