package org.servicetwo.exception;

public class CustomArithmeticException extends Exception{

    int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public CustomArithmeticException() {
    }

    public CustomArithmeticException(int statusCode, String message) {
        super(message);
        this.statusCode=statusCode;
    }
}
