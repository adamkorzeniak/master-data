package com.adamkorzeniak.masterdata.exception.exceptions;

public class NotSupportedSearchFieldClass extends RuntimeException {

    public NotSupportedSearchFieldClass(Class<?> fieldClass) {
        super("Not supported Search Field Class: " + fieldClass.getName());
    }
}
