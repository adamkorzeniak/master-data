package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.api.SearchFunctionType;

/**
 * Exception is thrown when trying to handle InvalidQueryParamValueException,
 * but error message for the function was not defined
 */
public class FunctionUnsupportedValueMessageNotDefinedException extends RuntimeException {

    private static final long serialVersionUID = 8029573087418204919L;

    public FunctionUnsupportedValueMessageNotDefinedException(SearchFunctionType function) {
        super("INTERNAL SERVER ERROR: Invalid Query Param Value Handling Error. " + function
            + " doesn't have supported value type message defined");
    }
}
