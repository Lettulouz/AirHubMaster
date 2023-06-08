package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's change password input
 */
public class ChangePasswordRequestDto {

    /**
     * Variable declaration
     */
    String token;
    String newPassword;
    String confirmedNewPassword;

    public ChangePasswordRequestDto(String token, String newPassword, String confirmedNewPassword) {
        this.token = token;
        this.newPassword = newPassword;
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
