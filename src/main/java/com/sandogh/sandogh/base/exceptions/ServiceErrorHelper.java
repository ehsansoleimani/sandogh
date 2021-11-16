package com.sandogh.sandogh.base.exceptions;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Ehsan Soleimani
 */
@Component
public class ServiceErrorHelper {
    public static final String UNKNOWN_ERROR = "error.unknown";

    public static Locale EN_LOCALE = Locale.ENGLISH;
    public static Locale FA_LOCALE = new Locale("fa", "IR");


    private Map<Locale, ResourceBundle> resourceBundles;

    @PostConstruct
    public void initializeResourceBundles() {
        resourceBundles = Map.of(EN_LOCALE, ResourceBundle.getBundle("messages.service-errors", EN_LOCALE),
                FA_LOCALE, ResourceBundle.getBundle("messages.service-errors", FA_LOCALE));
    }

    public static ServiceException unknownError(Throwable cause) {
        return new ServiceException(ServiceErrorHelper.UNKNOWN_ERROR, cause);
    }

    public String getErrorMessage(ServiceException e, Locale locale) {
        Validate.notNull(e);
        Validate.notNull(locale);
        if (resourceBundles == null) {
            throw new IllegalStateException("resource bundle has not been initialized yet.");
        }
        if (!resourceBundles.containsKey(locale)) {
            throw new IllegalArgumentException("locale '" + locale + " is not supported");
        }
        String errorMessage = resourceBundles.get(locale).getString(e.getErrorKey());

        if (e.getParams() != null && e.getParams().length > 0) {
            return MessageFormat.format(errorMessage, e.getParams());
        }
        return errorMessage;

    }

    public boolean handleError(ServiceException serviceException, String errorCode, ServiceErrorConsumer consumer) {
        Validate.notNull(serviceException);
        Validate.notNull(errorCode);
        Validate.notBlank(errorCode);
        Validate.notNull(consumer);
        if (errorCode.equals(serviceException.getErrorKey())) {
            consumer.handle(errorCode, serviceException.getParams());
            return true;
        }
        return false;
    }

    @FunctionalInterface
    public interface ServiceErrorConsumer {
        void handle(String errorCode, Object[] params);
    }


}
