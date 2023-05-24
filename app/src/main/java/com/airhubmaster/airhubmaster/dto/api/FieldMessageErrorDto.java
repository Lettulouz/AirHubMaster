package com.airhubmaster.airhubmaster.dto.api;

import java.util.HashMap;

/**
 * The helper class responsible for mapping error data of entered user fields from the server
 */
public class FieldMessageErrorDto {

    /**
     * Variable declaration
     */
    String timestamp;
    int status;
    String error;
    String method;
    HashMap<String, String> errors;

    public FieldMessageErrorDto(String timestamp, int status, String error, String method, HashMap<String, String> errors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.method = method;
        this.errors = errors;
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

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
    }
}
