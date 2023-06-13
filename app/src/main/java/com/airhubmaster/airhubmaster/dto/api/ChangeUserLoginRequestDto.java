package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's change login input in the main menu
 */
public class ChangeUserLoginRequestDto {

    /**
     * Variable declaration
     */
    String newLogin;

    public ChangeUserLoginRequestDto(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }
}
