package com.adamkorzeniak.masterdata.exception;

public class InvalidQueryParamException extends RuntimeException {

	public InvalidQueryParamException(String queryParam) {
		super("Invalid query param: " + queryParam);
	}
	
}
