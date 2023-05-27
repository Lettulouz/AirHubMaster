package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping standard message data from the server after
 * successful user account activate
 */
public class ActivateAccountResponseDto {

    /**
     * Variable declaration
     */
    String message;

    public ActivateAccountResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
