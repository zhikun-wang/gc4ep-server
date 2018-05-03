package com.r2d2.common.exception;

/**
 * Created by tim on 29/06/2017.
 */
public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }
}
