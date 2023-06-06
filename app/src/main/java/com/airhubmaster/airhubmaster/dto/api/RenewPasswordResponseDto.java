package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping standard message data from the server after
 * successful user account renew password
 */
public class RenewPasswordResponseDto {

    /**
     * Variable declaration
     */
    String message;

    public RenewPasswordResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
