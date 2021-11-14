package com.sandogh.sandogh.base.exceptions;

import org.springframework.stereotype.Component;

@Component
public class ServiceErrorHelper {
    public static final String UNKNOWN_ERROR = "error.unknown";

    public static ServiceException unknownError(Throwable cause) {
        return new ServiceException(ServiceErrorHelper.UNKNOWN_ERROR, cause);
    }

    public String getErrorMessage(ServiceException e) {
        return null;// TODO: 14.11.21 read error messages from properties file
    }
}
