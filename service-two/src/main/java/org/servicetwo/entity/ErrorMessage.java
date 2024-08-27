package org.servicetwo.entity;

public class ErrorMessage {
    private int statusCode;
    private String message;

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public ErrorMessage(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public ErrorMessage(){}
}
