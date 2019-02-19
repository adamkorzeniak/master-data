package com.adamkorzeniak.masterdata.exception;

import com.adamkorzeniak.masterdata.common.FilterFunction;

public class FilterNotSupportedException extends RuntimeException {

	public FilterNotSupportedException(FilterFunction function) {
		super("This resource doesn't support " + function.toString());
	}
}
