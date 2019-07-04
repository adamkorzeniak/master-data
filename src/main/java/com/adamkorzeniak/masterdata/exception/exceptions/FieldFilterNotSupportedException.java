package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.api.SearchFunctionType;

/**
 * 
 * Exception is thrown if function (i.e. MAX) is not supported for given API
 * Resource for specific field (i.e. title)
 *
 */
public class FieldFilterNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = 130231792029130784L;

	public FieldFilterNotSupportedException(SearchFunctionType function, String field) {
		super("This resource doesn't support " + function.toString() + " for field: " + field);
	}
}
