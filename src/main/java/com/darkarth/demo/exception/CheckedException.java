package com.darkarth.demo.exception;

import org.slf4j.Logger;

public class CheckedException extends Exception {

    private Logger LOGGER;

    private static final long serialVersionUID = 1L;

    private static final String BASE_MSG = "[%s] - %s: %s";

    private String errorCode;

    /**
     * 
     * @param errorCode Error code for the exception
     * @param exMsg Exception message
     * @param msg Detailed message
     */
    public CheckedException(String errorCode, String exMsg, String msg, Logger logger) {
        super(String.format(BASE_MSG, errorCode, exMsg, msg));
        this.errorCode = errorCode;
        LOGGER = logger;
        LOGGER.error(this.getLocalizedMessage());
    }

    /**
     * 
     * @param errorCode Error code for the exception
     * @param exMsg Exception message
     * @param msg Detailed message
     * @param t exception thrown
     */
    public CheckedException(String errorCode, String exMsg, String msg, Throwable t, Logger logger) {
        super(String.format(BASE_MSG, errorCode, exMsg, msg), t);
        this.errorCode = errorCode;
        LOGGER = logger;
        LOGGER.error(this.getLocalizedMessage());
    }

    /**
     * Returns error code of the exception.
     * @return errorCode Error code of the exception
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the error code of the exception.
     * @param errorCode Error code of the exception.
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}