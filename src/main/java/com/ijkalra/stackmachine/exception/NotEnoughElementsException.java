package com.ijkalra.stackmachine.exception;

public class NotEnoughElementsException extends Exception {

    public NotEnoughElementsException(String msg) {
        super(msg);
    }
    public NotEnoughElementsException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
