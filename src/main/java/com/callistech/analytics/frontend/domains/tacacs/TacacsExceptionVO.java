package com.callistech.analytics.frontend.domains.tacacs;

public class TacacsExceptionVO extends AAAConfigurationExceptionVO {

	private static final long serialVersionUID = -8137247071742617291L;

	public TacacsExceptionVO() {
	}

	public TacacsExceptionVO(String message) {
		super(message);
	}

	public TacacsExceptionVO(Throwable cause) {
		super(cause);
	}

	public TacacsExceptionVO(String message, Throwable cause) {
		super(message, cause);
	}

}
