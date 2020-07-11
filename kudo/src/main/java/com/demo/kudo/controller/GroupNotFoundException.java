package com.demo.kudo.controller;

public class GroupNotFoundException extends RuntimeException{

	public GroupNotFoundException() {
	}

	public GroupNotFoundException(String message) {
		super(message);
	}

	public GroupNotFoundException(Throwable cause) {
		super(cause);
	}

	public GroupNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
