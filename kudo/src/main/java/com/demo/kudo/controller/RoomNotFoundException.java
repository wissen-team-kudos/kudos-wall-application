package com.demo.kudo.controller;

public class RoomNotFoundException extends RuntimeException{

	public RoomNotFoundException() {
	}

	public RoomNotFoundException(String message) {
		super(message);
	}

	public RoomNotFoundException(Throwable cause) {
		super(cause);
	}

	public RoomNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RoomNotFoundException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
