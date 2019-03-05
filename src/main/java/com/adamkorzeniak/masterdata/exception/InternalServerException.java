package com.adamkorzeniak.masterdata.exception;

public class InternalServerException extends RuntimeException {

	public InternalServerException(String message) {
		super("Internal Server Error: " + message);
	}
}
