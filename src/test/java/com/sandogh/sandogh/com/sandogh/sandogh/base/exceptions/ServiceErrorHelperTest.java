package com.sandogh.sandogh.com.sandogh.sandogh.base.exceptions;

import com.sandogh.sandogh.base.exceptions.ServiceErrorHelper;
import com.sandogh.sandogh.base.exceptions.ServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.*;

public class ServiceErrorHelperTest {
    private ServiceErrorHelper serviceErrorHelper;

    @Before
    public void init() {
        serviceErrorHelper = new ServiceErrorHelper();
        serviceErrorHelper.initializeResourceBundles();
    }

    @Test
    public void testGetStringNoParamsEn() {
        String errorKey = "exampleModule.exampleCodeNoParams";
        ServiceException serviceException = new ServiceException(errorKey);

        String message = serviceErrorHelper.getErrorMessage(serviceException, ServiceErrorHelper.EN_LOCALE);

        assertEquals("example message", message);
    }

    @Test
    public void testGetStringWithParamsEn() {
        String errorKey = "exampleModule.exampleCodeWithParams";
        ServiceException serviceException = new ServiceException(errorKey, 1, "p2");

        String message = serviceErrorHelper.getErrorMessage(serviceException, ServiceErrorHelper.EN_LOCALE);

        assertEquals("example message with params: 1 'p2'", message);
    }

    @Test
    public void testGetStringNoParamFa() {
        String errorKey = "exampleModule.exampleCodeNoParams";
        ServiceException serviceException = new ServiceException(errorKey);

        String message = serviceErrorHelper.getErrorMessage(serviceException, ServiceErrorHelper.FA_LOCALE);

        assertEquals("نمونه پیام", message);
    }

    @Test
    public void testGetStringWithParamFa() {
        String errorKey = "exampleModule.exampleCodeWithParams";
        ServiceException serviceException = new ServiceException(errorKey, 1, "p2");

        String message = serviceErrorHelper.getErrorMessage(serviceException, ServiceErrorHelper.FA_LOCALE);

        assertEquals("نمونه پیام 1 'p2'", message);
    }

    @Test
    public void testHandleError() {
        String errorKey = "exampleModule.exampleCodeNoParams";
        ServiceException serviceException = new ServiceException(errorKey);

        CountDownLatch handlerExecuted = new CountDownLatch(1);

        boolean handled = serviceErrorHelper.handleError(serviceException, errorKey, new ServiceErrorHelper.ServiceErrorConsumer() {
            @Override
            public void handle(String errorCode, Object[] params) {
                handlerExecuted.countDown();
            }
        });

        assertTrue(handled);
        assertEquals(0, handlerExecuted.getCount());
    }
}
