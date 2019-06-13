package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * 
 * Exception is thrown if query param is incorrectly build (i.e. maxYear instead of max-year) 
 *
 */
public class InvalidQueryParamException extends RuntimeException {

	private static final long serialVersionUID = 4895013199752255470L;

	public InvalidQueryParamException(String queryParam) {
		super("Invalid query param: " + queryParam);
	}
}
