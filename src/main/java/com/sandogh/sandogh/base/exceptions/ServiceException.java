package com.sandogh.sandogh.base.exceptions;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
public class ServiceException extends Exception {
    private final String errorKey;
    private Object[] params;

    public ServiceException(String errorKey) {
        super();
        this.errorKey = errorKey;
    }

    public ServiceException(String errorKey, Object... params) {
        super();
        this.errorKey = errorKey;
        this.params = params;
    }

    public ServiceException(Throwable cause, String errorKey, Object... params) {
        super();
        this.errorKey = errorKey;
        this.params = params;
    }

    public ServiceException(String errorKey, Throwable cause) {
        super(cause);
        this.errorKey = errorKey;
    }
}
