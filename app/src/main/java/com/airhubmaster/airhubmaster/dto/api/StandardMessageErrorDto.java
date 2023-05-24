package com.airhubmaster.airhubmaster.dto.api;

/**
 * The helper class responsible for mapping standard error data from the server
 */
public class StandardMessageErrorDto {

    /**
     * Variable declaration
     */
    String timestamp;
    int status;
    String error;
    String method;
    String message;

    public StandardMessageErrorDto(String timestamp, int status, String error, String method, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.method = method;
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
