package com.adamkorzeniak.masterdata.movie.exception;

public class NotFoundGenreInMovieException extends RuntimeException {

	public NotFoundGenreInMovieException() {
		super();
	}

	public NotFoundGenreInMovieException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotFoundGenreInMovieException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundGenreInMovieException(String message) {
		super(message);
	}

	public NotFoundGenreInMovieException(Throwable cause) {
		super(cause);
	}

	
}
