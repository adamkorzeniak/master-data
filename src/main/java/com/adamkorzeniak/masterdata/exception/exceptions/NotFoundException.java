package com.adamkorzeniak.masterdata.exception.exceptions;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String objectName, Long id) {
        super(objectName + " not found: id=" + id);
    }
}
