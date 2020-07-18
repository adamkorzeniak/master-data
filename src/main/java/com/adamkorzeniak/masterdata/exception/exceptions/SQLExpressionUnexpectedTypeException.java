package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.api.basic.filter.FilterExpressionTokenType;

public class SQLExpressionUnexpectedTypeException extends RuntimeException {

    public SQLExpressionUnexpectedTypeException(FilterExpressionTokenType type) {
        super("Invalid order expression element with type: " + type);
    }
}
