package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.FilterFunction;

public class InvalidQueryParamValueException extends RuntimeException {

	public InvalidQueryParamValueException(FilterFunction function, String queryParam) {
		super("Invalid query param value: " + queryParam + ". " + function.toString()
				+ " supports only numeric values.");
	}

}
