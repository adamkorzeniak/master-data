package com.adamkorzeniak.masterdata.movie.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.adamkorzeniak.masterdata.exception.ExceptionResponse;

@ControllerAdvice
public class MovieResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { GenreAlreadyInMovieException.class })
	protected ResponseEntity<Object> genreAlreadyInMovieException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				"REQ404",
				"Bad Request",
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	@ExceptionHandler(value = { NotFoundGenreInMovieException.class })
	protected ResponseEntity<Object> notFoundGenreInMovieException(RuntimeException exc, WebRequest request) {
		ExceptionResponse bodyOfResponse = new ExceptionResponse(
				"REQ000",
				"Bad Request",
				exc.getMessage());
		return handleExceptionInternal(exc, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

}