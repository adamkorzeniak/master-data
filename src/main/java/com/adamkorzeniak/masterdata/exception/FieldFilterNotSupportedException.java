package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.shared.FilterFunction;

public class FieldFilterNotSupportedException extends RuntimeException {

	public FieldFilterNotSupportedException(FilterFunction function, String field) {
		super("This resource doesn't support " + function.toString() + " for field: " + field);
	}

}
