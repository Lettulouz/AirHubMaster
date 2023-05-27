package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping expired jwt tokens from the user
 */
public class RefreshRequestDto {

    /**
     * Variable declaration
     */
    String expiredJwt;
    String refreshToken;

    public RefreshRequestDto(String expiredJwt, String refreshToken) {
        this.expiredJwt = expiredJwt;
        this.refreshToken = refreshToken;
    }

    public String getExpiredJwt() {
        return expiredJwt;
    }

    public void setExpiredJwt(String expiredJwt) {
        this.expiredJwt = expiredJwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
