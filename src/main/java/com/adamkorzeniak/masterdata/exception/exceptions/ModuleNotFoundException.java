package com.adamkorzeniak.masterdata.exception.exceptions;

public class ModuleNotFoundException extends RuntimeException {

    public ModuleNotFoundException(String moduleName) {
        super("Module: " + moduleName + " could not be found. It was caused either by programming bug or incorrect openapi definition");
    }
}
