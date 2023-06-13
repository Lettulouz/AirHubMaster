package com.airhubmaster.airhubmaster.dto.api;

/**
 * A helper class responsible for mapping the standard message data from the server to the
 * successful entry of the user's login change in the main menu
 */
public class ChangeUserLoginResponseDto {

    /**
     * Variable declaration
     */
    String newLogin;
    String message;
    String updatedJwt;

    public ChangeUserLoginResponseDto(String newLogin, String message, String updatedJwt) {
        this.newLogin = newLogin;
        this.message = message;
        this.updatedJwt = updatedJwt;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public void setNewLogin(String newLogin) {
        this.newLogin = newLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUpdatedJwt() {
        return updatedJwt;
    }

    public void setUpdatedJwt(String updatedJwt) {
        this.updatedJwt = updatedJwt;
    }
}
