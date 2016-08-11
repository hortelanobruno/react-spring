package com.callistech.analytics.frontend.util;

import java.io.Serializable;

/**
 *
 * @author pablo
 */
public class AjaxResult implements Serializable {

	private Integer errorCode;
	private String message;
	private Object result;

	public AjaxResult() {
		errorCode = 0;
		message = "Successful request";
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 89 * hash + (this.errorCode != null ? this.errorCode.hashCode() : 0);
		hash = 89 * hash + (this.message != null ? this.message.hashCode() : 0);
		hash = 89 * hash + (this.result != null ? this.result.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final AjaxResult other = (AjaxResult) obj;
		if (this.errorCode != other.errorCode && (this.errorCode == null || !this.errorCode.equals(other.errorCode))) {
			return false;
		}
		if ((this.message == null) ? (other.message != null) : !this.message.equals(other.message)) {
			return false;
		}
		if (this.result != other.result && (this.result == null || !this.result.equals(other.result))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "AjaxResult{" + "errorCode=" + errorCode + ", message=" + message + ", result=" + result + '}';
	}
}
