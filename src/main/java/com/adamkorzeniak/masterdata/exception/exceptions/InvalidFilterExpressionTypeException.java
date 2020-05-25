package com.adamkorzeniak.masterdata.exception.exceptions;

public class InvalidFilterExpressionTypeException extends RuntimeException {

    public InvalidFilterExpressionTypeException(String filterType) {
        super("Invalid filter expression element with type: " + filterType);
    }
}
