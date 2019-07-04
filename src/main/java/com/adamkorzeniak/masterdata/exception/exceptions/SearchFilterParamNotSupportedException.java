package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.api.SearchFunctionType;

/**
 * 
 * Exception is thrown given resource doesn't support provided Search Filter
 * Function (i.e. MATCH)
 *
 */
public class SearchFilterParamNotSupportedException extends RuntimeException {

	private static final long serialVersionUID = -3163362170824270895L;

	public SearchFilterParamNotSupportedException(SearchFunctionType function) {
		super("This resource doesn't support " + function.toString());
	}

}
