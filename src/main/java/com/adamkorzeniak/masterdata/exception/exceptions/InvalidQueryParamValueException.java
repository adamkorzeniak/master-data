package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.api.SearchFunctionType;

/**
 * 
 * Exception is thrown if query param value is of not supported type 
 * (i.e. "Titanic" as max-duration, where it should be numeric)
 *
 */
public class InvalidQueryParamValueException extends RuntimeException {

	private static final long serialVersionUID = -6248846780550800284L;

	public InvalidQueryParamValueException(SearchFunctionType function, String queryParam) {
		super(buildMessage(function, queryParam));
	}
	
	private static String buildMessage(SearchFunctionType function, String queryParam) {
		String supportedType = "";
		switch (function) {
			case EXIST:
				supportedType = "boolean";
				break;
			case MIN:
			case MAX:
				supportedType = "numeric";
				break;
			default:
				throw new FunctionUnsupportedValueMessageNotDefinedException(function);
		}
		return String.format("Invalid query param value for '%s'. %s supports only %s values.",
				queryParam,
				function.toString(),
				supportedType);
	}

}
