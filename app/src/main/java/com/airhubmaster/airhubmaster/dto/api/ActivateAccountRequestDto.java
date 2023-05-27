package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's activate account input
 */
public class ActivateAccountRequestDto {

    /**
     * Variable declaration
     */
    String token;

    public ActivateAccountRequestDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
