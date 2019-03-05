package com.adamkorzeniak.masterdata.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adamkorzeniak.masterdata.common.model.ExceptionResponse;
import com.adamkorzeniak.masterdata.exception.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.FilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.InternalServerException;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamValueException;
import com.adamkorzeniak.masterdata.exception.NotFoundException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	public static final String NOT_FOUND_CODE = "REQ404";
	public static final String NOT_FOUND_TITLE = "Not Found";
	public static final String INVALID_QUERY_PARAM_CODE = "REQ001";
	public static final String INVALID_QUERY_PARAM_TITLE = "Invalid Query Param";
	public static final String INVALID_QUERY_PARAM_VALUE_CODE = "REQ002";
	public static final String INVALID_QUERY_PARAM_VALUE_TITLE = "Invalid Query Param Value";
	public static final String FILTER_NOT_SUPPORTED_CODE = "REQ003";
	public static final String FILTER_NOT_SUPPORTED_TITLE = "Filter Not Supported";
	public static final String FIELD_FILTER_NOT_SUPPORTED_CODE = "REQ004";
	public static final String FIELD_FILTER_NOT_SUPPORTED_TITLE = "Filter Field Not Supported";
	public static final String BAD_REQUEST_CODE = "REQ000";
	public static final String BAD_REQUEST_TITLE = "Bad Request";
	public static final String INTERNAL_SERVER_ERROR_CODE = "ERR000";
	public static final String INTERNAL_SERVER_ERROR_TITLE = "Internal Server Error";

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> notFound(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(NOT_FOUND_CODE, NOT_FOUND_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { InvalidQueryParamException.class })
	protected ResponseEntity<Object> invalidQueryParamException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INVALID_QUERY_PARAM_CODE, INVALID_QUERY_PARAM_TITLE,
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { InvalidQueryParamValueException.class })
	protected ResponseEntity<Object> invalidQueryParamValueException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INVALID_QUERY_PARAM_VALUE_CODE,
				INVALID_QUERY_PARAM_VALUE_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { FilterNotSupportedException.class })
	protected ResponseEntity<Object> filterNotSupportedException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(FILTER_NOT_SUPPORTED_CODE, FILTER_NOT_SUPPORTED_TITLE,
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { FieldFilterNotSupportedException.class })
	protected ResponseEntity<Object> filterFieldNotSupportedException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(FIELD_FILTER_NOT_SUPPORTED_CODE,
				FIELD_FILTER_NOT_SUPPORTED_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { InternalServerException.class })
	protected ResponseEntity<Object> internalServerException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INTERNAL_SERVER_ERROR_CODE,
				INTERNAL_SERVER_ERROR_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value = { Throwable.class })
	protected ResponseEntity<Object> defaultException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(BAD_REQUEST_CODE, BAD_REQUEST_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
}