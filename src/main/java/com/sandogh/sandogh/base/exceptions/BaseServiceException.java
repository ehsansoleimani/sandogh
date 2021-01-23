package com.sandogh.sandogh.base.exceptions;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
public abstract class BaseServiceException extends Exception {
    public BaseServiceException() {
        super();
    }

    public BaseServiceException(Throwable cause) {
        super(cause);
    }

    public abstract String getMessageResourceKey();

    public abstract Object[] getMessageParams();
}
