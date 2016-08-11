package com.callistech.analytics.frontend.domains.tacacs;

public class AAAConfigurationExceptionVO extends Exception {

    private static final long serialVersionUID = -8137247071742617291L;

    public AAAConfigurationExceptionVO() {
    }

    public AAAConfigurationExceptionVO(String message) {
        super(message);
    }

    public AAAConfigurationExceptionVO(Throwable cause) {
        super(cause);
    }

    public AAAConfigurationExceptionVO(String message, Throwable cause) {
        super(message, cause);
    }

}
