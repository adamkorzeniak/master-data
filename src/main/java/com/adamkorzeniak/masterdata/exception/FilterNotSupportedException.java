package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.api.SearchFunctionType;

public class FilterNotSupportedException extends RuntimeException {

	public FilterNotSupportedException(SearchFunctionType function) {
		super("This resource doesn't support " + function.toString());
	}
	
}
