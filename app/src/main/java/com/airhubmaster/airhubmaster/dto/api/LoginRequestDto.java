package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's login inputs
 */
public class LoginRequestDto {

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

    public LoginRequestDto(String loginOrEmail, String password) {
        this.loginOrEmail = loginOrEmail;
        this.password = password;
    }
}
