package org.easyJsonApi.exceptions;

public class EasyJsonApiMalformedJsonException extends EasyJsonApiException {

    /**
     * UID Generated
     */
    private static final long serialVersionUID = -5386584049560908534L;

    public EasyJsonApiMalformedJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public EasyJsonApiMalformedJsonException(String message) {
        super(message);
    }

}
