package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.api.SearchFunctionType;

public class InvalidQueryParamValueException extends RuntimeException {

	public InvalidQueryParamValueException(SearchFunctionType function, String queryParam) {
		super("Invalid query param value: " + queryParam + ". " + function.toString()
				+ " supports only "
				+ ((function == SearchFunctionType.EXIST) ? "boolean" : "numeric")
				+ " values.");
	}

}
