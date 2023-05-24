package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping jwt tokens from the server
 */
public class LoginResponseDto {

    /**
     * Variable declaration
     */
    String jwtToken;
    String refreshToken;

    public LoginResponseDto(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
