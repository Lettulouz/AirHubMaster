package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's change password
 * input in the main menu
 */
public class ChangeUserPasswordRequestDto {

    /**
     * Variable declaration
     */
    String oldPassword;
    String newPassword;
    String confirmedNewPassword;

    public ChangeUserPasswordRequestDto(String oldPassword, String newPassword, String confirmedNewPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
