package com.env.pro.util.exception;

/**
 Created by Zhikun on 13/01/2018. */
public class SocketOperatorException extends RuntimeException {

    public SocketOperatorException() {
        super();
    }

    public SocketOperatorException(String message) {
        super(message);
    }


    public SocketOperatorException(String message, Throwable cause) {
        super(message, cause);
    }


    public SocketOperatorException(Throwable cause) {
        super(cause);
    }

}
