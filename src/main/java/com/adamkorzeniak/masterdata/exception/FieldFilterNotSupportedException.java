package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.api.SearchFunctionType;

public class FieldFilterNotSupportedException extends RuntimeException {

	public FieldFilterNotSupportedException(SearchFunctionType function, String field) {
		super("This resource doesn't support " + function.toString() + " for field: " + field);
	}
}
