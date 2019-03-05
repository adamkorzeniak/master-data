package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.api.SearchFunctionType;

public class InvalidQueryParamValueException extends RuntimeException {

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
				String message = "Invalid Query Param Value Handling Error. "
						+ function + " doesn't have supported value type message defined";
				throw new InternalServerException(message);
		}
		return String.format("Invalid query param value: %s. %s supports only %s values.",
				queryParam,
				function.toString(),
				supportedType);
	}

}
