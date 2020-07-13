package com.demo.kudo.controller;

public class KudosNotFoundException extends RuntimeException {

	public KudosNotFoundException() {
	}

	public KudosNotFoundException(String message) {
		super(message);
	}

	public KudosNotFoundException(Throwable cause) {
		super(cause);
	}

	public KudosNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public KudosNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
