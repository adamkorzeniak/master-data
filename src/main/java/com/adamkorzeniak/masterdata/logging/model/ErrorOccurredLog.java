package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorOccurredLog implements LogType {

    private static final String TYPE_MESSAGE = "ERROR_OCCURRED";

    private final String methodName;
    private final List<Throwable> errors;
    private final String message;

    public ErrorOccurredLog(String methodName, List<Throwable> errors) {
        this.methodName = methodName;
        this.errors = errors;
        this.message = "Error occurred";
    }

    @Override
    public String getType() {
        return TYPE_MESSAGE;
    }
}
