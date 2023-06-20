package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for data mapping from the user's delete account input
 */
public class DeleteUserAccountRequestDto {

    /**
     * Variable declaration
     */
    String password;

    public DeleteUserAccountRequestDto(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
