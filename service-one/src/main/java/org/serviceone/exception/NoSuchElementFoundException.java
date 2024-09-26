package org.serviceone.exception;

public class NoSuchElementFoundException extends Exception{

    int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public NoSuchElementFoundException() {
    }

    public NoSuchElementFoundException(int statusCode, String message) {
        super(message);
        this.statusCode=statusCode;
    }
}
