package com.adamkorzeniak.masterdata.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adamkorzeniak.masterdata.exception.exceptions.DuplicateUserException;
import com.adamkorzeniak.masterdata.exception.exceptions.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.FunctionUnsupportedValueMessageNotDefinedException;
import com.adamkorzeniak.masterdata.exception.exceptions.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.exceptions.InvalidQueryParamValueException;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.exception.exceptions.PatchOperationNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamsNotInitializedException;

@ControllerAdvice
public class GlobalResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	 * Error Codes:
	 * Build by
	 * - 3 letters - specifying error cause type (i.e. ERR)
	 * - 3 numbers - code corresponding to specific error
	 * 
	 * Error Type:
	 * - REQ - request build incorrectly, needs to be corrected by client and sent again
	 * - ERR - internal server error (bug), needs to be fixed by server developer
	 * 
	 */

	private static final String BAD_REQUEST_CODE = "REQ000";
	private static final String BAD_REQUEST_TITLE = "Bad Request";
	private static final String NOT_FOUND_CODE = "REQ001";
	private static final String NOT_FOUND_TITLE = "Not Found";
	private static final String METHOD_NOT_ALLOWED_CODE = "REQ002";
	private static final String METHOD_NOT_ALLOWED_TITLE = "Method Not Allowed";
	private static final String MESSAGE_NOT_READABLE_CODE = "REQ003";
	private static final String MESSAGE_NOT_READABLE_TITLE = "Message Not Readable";
	private static final String MEDIA_TYPE_NOT_SUPPORTED_CODE = "REQ004";
	private static final String MEDIA_TYPE_NOT_SUPPORTED_TITLE = "Media Type Not Supported";
	
	
	private static final String INVALID_QUERY_PARAM_CODE = "REQ100";
	private static final String INVALID_QUERY_PARAM_TITLE = "Invalid Query Param";
	private static final String INVALID_QUERY_PARAM_VALUE_CODE = "REQ101";
	private static final String INVALID_QUERY_PARAM_VALUE_TITLE = "Invalid Query Param Value";
	private static final String FILTER_NOT_SUPPORTED_CODE = "REQ102";
	private static final String FILTER_NOT_SUPPORTED_TITLE = "Filter Not Supported";
	private static final String FIELD_FILTER_NOT_SUPPORTED_CODE = "REQ103";
	private static final String FIELD_FILTER_NOT_SUPPORTED_TITLE = "Filter Field Not Supported";
	private static final String PATCH_OPERATION_NOT_SUPPORTED_CODE = "REQ104";
	private static final String PATCH_OPERATION_NOT_SUPPORTED_TITLE = "Patch Operation Not Supported";
	

	private static final String DUPLICATE_ENTRY_CODE = "REQ200";
	private static final String DUPLICATE_ENTRY_TITLE = "Duplicate Entry";
	
	private static final String INTERNAL_SERVER_ERROR_CODE = "ERR000";
	private static final String INTERNAL_SERVER_ERROR_TITLE = "Unexpected Internal Server Error";
	
	private static final String ERROR_UNSUPPORTED_FUNCTION_MESSAGE_CODE = "ERR001";
	private static final String ERROR_UNSUPPORTED_FUNCTION_MESSAGE_TITLE = "Function Unsupported Value Message Not Defined";
	
	private static final String ERROR_SEARCH_PARAMS_NOT_INITIALIZED_CODE = "ERR002";
	private static final String ERROR_SEARCH_PARAMS_NOT_INITIALIZED_TITLE = "Search Filter Params Not Initialized";
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = exc.getBindingResult().getFieldErrors().stream()
				.map(this::buildModelFieldErrorMessage)
				.collect(Collectors.joining("\n"));
		ExceptionResponse bodyOfResponse = new ExceptionResponse(BAD_REQUEST_CODE, BAD_REQUEST_TITLE, message);
		return handleExceptionInternal(exc, bodyOfResponse, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(METHOD_NOT_ALLOWED_CODE, METHOD_NOT_ALLOWED_TITLE, ex.getMessage());
		return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String message = ex.getMessage();
		ExceptionResponse bodyOfResponse = new ExceptionResponse(MESSAGE_NOT_READABLE_CODE, MESSAGE_NOT_READABLE_TITLE, message);
		return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(MEDIA_TYPE_NOT_SUPPORTED_CODE, MEDIA_TYPE_NOT_SUPPORTED_TITLE, ex.getMessage());
		return handleExceptionInternal(ex, bodyOfResponse, headers, status, request);
	}

	@ExceptionHandler(value = { DuplicateUserException.class })
	protected ResponseEntity<Object> duplicateUser(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(DUPLICATE_ENTRY_CODE, DUPLICATE_ENTRY_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	@ExceptionHandler(value = { NotFoundException.class })
	protected ResponseEntity<Object> notFound(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(NOT_FOUND_CODE, NOT_FOUND_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value = { InvalidQueryParamException.class })
	protected ResponseEntity<Object> invalidQueryParamException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INVALID_QUERY_PARAM_CODE, INVALID_QUERY_PARAM_TITLE,
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { InvalidQueryParamValueException.class })
	protected ResponseEntity<Object> invalidQueryParamValueException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INVALID_QUERY_PARAM_VALUE_CODE,
				INVALID_QUERY_PARAM_VALUE_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { SearchFilterParamNotSupportedException.class })
	protected ResponseEntity<Object> filterNotSupportedException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(FILTER_NOT_SUPPORTED_CODE, FILTER_NOT_SUPPORTED_TITLE,
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { FieldFilterNotSupportedException.class })
	protected ResponseEntity<Object> filterFieldNotSupportedException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(FIELD_FILTER_NOT_SUPPORTED_CODE,
				FIELD_FILTER_NOT_SUPPORTED_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value = { PatchOperationNotSupportedException.class })
	protected ResponseEntity<Object> patchOperationNotSupportedException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(PATCH_OPERATION_NOT_SUPPORTED_CODE,
				PATCH_OPERATION_NOT_SUPPORTED_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	@ExceptionHandler(value = { FunctionUnsupportedValueMessageNotDefinedException.class })
	protected ResponseEntity<Object> functionUnsupportedValueMessageNotDefined(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(ERROR_UNSUPPORTED_FUNCTION_MESSAGE_CODE,
				ERROR_UNSUPPORTED_FUNCTION_MESSAGE_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value = { SearchFilterParamsNotInitializedException.class })
	protected ResponseEntity<Object> searchFilterParamsNotInitialized(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(ERROR_SEARCH_PARAMS_NOT_INITIALIZED_CODE,
				ERROR_SEARCH_PARAMS_NOT_INITIALIZED_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	@ExceptionHandler(value = { Throwable.class })
	protected ResponseEntity<Object> defaultException(Exception exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(INTERNAL_SERVER_ERROR_CODE, INTERNAL_SERVER_ERROR_TITLE, exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	private String buildModelFieldErrorMessage(FieldError error) {
		return String.format("Invalid '%s' field value: %s. Field '%s' %s.",
				error.getField(),
				error.getRejectedValue(),
				error.getField(),
				error.getDefaultMessage());
	}
}