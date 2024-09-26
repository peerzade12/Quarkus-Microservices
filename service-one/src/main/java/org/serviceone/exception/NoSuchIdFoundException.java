package org.serviceone.exception;

public class NoSuchIdFoundException extends Exception{

    int statusCode;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public NoSuchIdFoundException() {
    }

    public NoSuchIdFoundException(int statusCode, String message) {
        super(message);
        this.statusCode=statusCode;
    }
}
