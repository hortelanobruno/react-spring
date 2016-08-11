package com.callistech.analytics.frontend.domains.uc.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Indicates a resource was not found.
 * 
 * @author Jon Brisbin
 * @author Oliver Gierke
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DomainModelException extends RuntimeException {

	private static final long serialVersionUID = 7992904489502842099L;

	public DomainModelException() {
		this("Resource not found!");
	}

	public DomainModelException(String message) {
		this(message, null);
	}

	public DomainModelException(String message, Throwable cause) {
		super(message, cause);
	}
}