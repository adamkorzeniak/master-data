package com.adamkorzeniak.masterdata.exception.exceptions;

public class NotSupportedSearchFieldClassException extends RuntimeException {

    public NotSupportedSearchFieldClassException(Class<?> fieldClass) {
        super("Not supported Search Field Class: " + fieldClass.getName());
    }
}
