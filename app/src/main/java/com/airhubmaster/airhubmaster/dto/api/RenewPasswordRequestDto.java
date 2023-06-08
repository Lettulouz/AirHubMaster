package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's renew password input
 */
public class RenewPasswordRequestDto {

    /**
     * Variable declaration
     */
    String loginOrEmail;

    public RenewPasswordRequestDto(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }

    public String getLoginOrEmail() {
        return loginOrEmail;
    }

    public void setLoginOrEmail(String loginOrEmail) {
        this.loginOrEmail = loginOrEmail;
    }
}
