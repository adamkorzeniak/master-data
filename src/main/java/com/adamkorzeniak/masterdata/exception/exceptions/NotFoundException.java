package com.adamkorzeniak.masterdata.exception.exceptions;

/**
 * Exception is thrown when record with given id was not found
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String objectName, Long id) {
        super(objectName + " not found: id=" + id);
    }
}
