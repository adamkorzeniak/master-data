package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class MethodExitedLog implements LogType {

    private static final String TYPE_MESSAGE = "METHOD_EXITED";

    private final String methodName;
    private final String message;

    public MethodExitedLog(String methodName) {
        this.methodName = methodName;
        this.message = "Method exited";
    }

    @Override
    public String getType() {
        return TYPE_MESSAGE;
    }
}
