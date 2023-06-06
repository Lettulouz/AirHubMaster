package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping standard message data from the server after
 * successful user account change password
 */
public class ChangePasswordResponseDto {

    /**
     * Variable declaration
     */
    String message;

    public ChangePasswordResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
