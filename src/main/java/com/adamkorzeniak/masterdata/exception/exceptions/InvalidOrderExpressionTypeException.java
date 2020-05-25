package com.adamkorzeniak.masterdata.exception.exceptions;

public class InvalidOrderExpressionTypeException extends RuntimeException {

    public InvalidOrderExpressionTypeException(String filterType) {
        super("Invalid order expression element with type: " + filterType);
    }
}
