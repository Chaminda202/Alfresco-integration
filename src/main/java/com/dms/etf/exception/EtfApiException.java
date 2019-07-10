package com.dms.etf.exception;

public class EtfApiException extends Exception {
	private static final long serialVersionUID = -2763260624757048354L;

	public EtfApiException() {
		super();
	}

	public EtfApiException(String message, Throwable cause) {
		super(message, cause);
	}

	public EtfApiException(String message) {
		super(message);
	}
}
