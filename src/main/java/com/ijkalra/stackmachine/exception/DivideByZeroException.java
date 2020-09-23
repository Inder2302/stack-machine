package com.ijkalra.stackmachine.exception;

public class DivideByZeroException extends Exception{

    public DivideByZeroException(String msg) {
        super(msg);
    }

    public DivideByZeroException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
