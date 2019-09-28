package com.adamkorzeniak.masterdata.logging.model;

import lombok.Getter;

@Getter
public class RequestReceivedLog implements LogType {

    private static final String TYPE_MESSAGE = "REQUEST_RECEIVED";

    private final String message;

    public RequestReceivedLog() {
        this.message = "Request received";
    }

    @Override
    public String getType() {
        return TYPE_MESSAGE;
    }
}
