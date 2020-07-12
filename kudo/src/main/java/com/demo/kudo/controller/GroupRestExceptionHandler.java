package com.demo.kudo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {GroupController.class})
public class GroupRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<GroupErrorResponse> handleException(GroupNotFoundException exc) {
		
		GroupErrorResponse error = new GroupErrorResponse(
											HttpStatus.NOT_FOUND.value(),
											exc.getMessage(),
											System.currentTimeMillis()
											);
		
		return new ResponseEntity<GroupErrorResponse>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<GroupErrorResponse> handleException(Exception exc) {
		
		GroupErrorResponse error = new GroupErrorResponse(
											HttpStatus.BAD_REQUEST.value(),
											exc.getMessage(),
											System.currentTimeMillis()
											);
		
		return new ResponseEntity<GroupErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
	
}
