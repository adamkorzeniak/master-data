package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class InfoLog implements LogType {

    private static final String TYPE_MESSAGE = "INFO_MESSAGE";

    private final String message;

    public InfoLog(String message) {
        this.message = message;
    }

    @Override
    public String getType() {
        return TYPE_MESSAGE;
    }
}
