package com.sandogh.sandogh.users.exceptions;

import com.sandogh.sandogh.base.exceptions.BaseServiceException;

/**
 * @author Ehsan Soleimani (esoleimani@voipfuture.com)
 **/
public class UsernameAlreadyTakeException extends BaseServiceException {
    private final String username;

    public UsernameAlreadyTakeException(String username) {
        super();
        this.username = username;
    }

    @Override
    public String getMessageResourceKey() {
        return null;
    }

    @Override
    public Object[] getMessageParams() {
        return new Object[0];
    }
}
