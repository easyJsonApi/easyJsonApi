package org.easyJsonApi.exceptions;

public class EasyJsonApiException extends Exception {

    /**
     * UID Generated
     */
    private static final long serialVersionUID = -5544661923448271394L;

    public EasyJsonApiException(String message) {
        super(message);
    }

    public EasyJsonApiException(String message, Throwable cause) {
        super(message, cause);
    }

}
