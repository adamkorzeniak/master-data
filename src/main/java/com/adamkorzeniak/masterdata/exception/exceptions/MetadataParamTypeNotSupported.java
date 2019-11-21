package com.adamkorzeniak.masterdata.exception.exceptions;

public class MetadataParamTypeNotSupported extends RuntimeException {
    public MetadataParamTypeNotSupported(String paramTypeName) {
        super("Metadata Param Type '" + paramTypeName + " is invalid.");
    }
}
