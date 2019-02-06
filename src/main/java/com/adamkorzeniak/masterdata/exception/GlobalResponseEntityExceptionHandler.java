package com.adamkorzeniak.masterdata.exception;

import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	public static final String NOT_FOUND_CODE = "REQ404";
	public static final String NOT_FOUND_TITLE = "Not Found";

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> notFound(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				NOT_FOUND_CODE,
				NOT_FOUND_TITLE,
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = { IllegalArgumentException.class })
	protected ResponseEntity<Object> badRequest(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				"REQ000",
				"Bad Argument",
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	protected ResponseEntity<Object> dataInvalid(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				"REQ001",
				"Bad Request",
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { ConstraintViolationException.class })
	protected ResponseEntity<Object> constraintsViolated(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				"REQ002",
				"Bad Request",
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}