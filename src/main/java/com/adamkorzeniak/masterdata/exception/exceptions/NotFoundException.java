package com.adamkorzeniak.masterdata.exception.exceptions;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;

public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String objectName, Long id) {
        super(objectName + " not found: id=" + id);
    }

    public NotFoundException(String feature, String resource) {
        super("Resource /" + feature + "/" + resource + " not found");
    }

    public NotFoundException(Class<? extends DatabaseEntity> responseClass, Long id) {
        super("Entity of class: " + responseClass + " with id = " + id + " not found");
    }
}
