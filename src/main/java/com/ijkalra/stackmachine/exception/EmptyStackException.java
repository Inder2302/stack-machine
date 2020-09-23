package com.ijkalra.stackmachine.exception;

public class EmptyStackException extends Exception{

    public EmptyStackException(String msg) {
        super(msg);
    }
    public EmptyStackException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
