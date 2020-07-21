package com.demo.kudo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {RoomController.class})
public class RoomRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<RoomErrorResponse> handleException(RoomNotFoundException exc) {
		
		RoomErrorResponse error = new RoomErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis()
											);
		
		return new ResponseEntity<RoomErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<RoomErrorResponse> handleException(Exception exc) {
		
		RoomErrorResponse error = new RoomErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis()
											);
		
		return new ResponseEntity<RoomErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
}
