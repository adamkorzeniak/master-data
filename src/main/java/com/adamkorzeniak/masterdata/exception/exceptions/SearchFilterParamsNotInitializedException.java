package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * 
 * Exception is thrown when developer did not initialize an list of Search
 * Filter Params, when trying to use Specification Predicate
 *
 */
public class SearchFilterParamsNotInitializedException extends RuntimeException {

	private static final long serialVersionUID = -9121766875973354121L;

	public SearchFilterParamsNotInitializedException() {
		super("INTERNAL SERVER ERROR: Search Filter Params Not Initialized. If there were no params provided, please initialize empty list");
	}

}
