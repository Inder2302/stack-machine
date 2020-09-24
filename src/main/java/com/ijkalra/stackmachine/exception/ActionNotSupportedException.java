package com.ijkalra.stackmachine.exception;

public class ActionNotSupportedException extends Exception{
    public ActionNotSupportedException(String msg) {
        super(msg);
    }

    public ActionNotSupportedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
