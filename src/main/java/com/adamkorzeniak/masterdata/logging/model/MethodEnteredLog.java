package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class MethodEnteredLog implements LogType {

    private static final String TYPE_MESSAGE = "METHOD_ENTERED";

    private final String methodName;
    private final String message;

    public MethodEnteredLog(String methodName) {
        this.methodName = methodName;
        this.message = "Method entered";
    }

    @Override
    public String getType() {
        return TYPE_MESSAGE;
    }
}
