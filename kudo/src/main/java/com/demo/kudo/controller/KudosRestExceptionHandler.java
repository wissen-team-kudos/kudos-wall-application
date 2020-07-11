package com.demo.kudo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(assignableTypes = {KudosController.class})
public class KudosRestExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<KudosErrorResponse> handleException(KudosNotFoundException exc){
		KudosErrorResponse error = new KudosErrorResponse(HttpStatus.NOT_FOUND.value(),
									exc.getMessage(),
									System.currentTimeMillis());
		return new ResponseEntity<KudosErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<KudosErrorResponse> handleException(Exception exc){
		KudosErrorResponse error = new KudosErrorResponse(HttpStatus.BAD_REQUEST.value(),
									exc.getMessage(),
									System.currentTimeMillis());
		return new ResponseEntity<KudosErrorResponse>(error, HttpStatus.BAD_REQUEST);
	}
}
