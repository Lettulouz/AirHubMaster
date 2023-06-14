package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping standard message data from the server after
 * successful user account delete
 */
public class DeleteUserAccountResponseDto {

    /**
     * Variable declaration
     */
    String message;

    public DeleteUserAccountResponseDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
