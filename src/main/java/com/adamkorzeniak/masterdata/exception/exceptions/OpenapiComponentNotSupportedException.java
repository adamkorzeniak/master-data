package com.adamkorzeniak.masterdata.exception.exceptions;

public class OpenapiComponentNotSupportedException extends RuntimeException {

    public OpenapiComponentNotSupportedException(String componentReferenceType) {
        super("Openapi Component: '" + componentReferenceType + "' not supported.");
    }
}
