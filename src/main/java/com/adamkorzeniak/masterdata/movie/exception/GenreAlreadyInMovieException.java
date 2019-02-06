package com.adamkorzeniak.masterdata.movie.exception;

public class GenreAlreadyInMovieException extends RuntimeException {

	public GenreAlreadyInMovieException() {
		super();
	}

	public GenreAlreadyInMovieException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public GenreAlreadyInMovieException(String message, Throwable cause) {
		super(message, cause);
	}

	public GenreAlreadyInMovieException(String message) {
		super(message);
	}

	public GenreAlreadyInMovieException(Throwable cause) {
		super(cause);
	}

	
}
