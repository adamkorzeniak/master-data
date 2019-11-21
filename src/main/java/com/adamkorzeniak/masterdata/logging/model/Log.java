package com.adamkorzeniak.masterdata.logging.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.util.StringJoiner;

@Getter
@RequiredArgsConstructor
public class Log {

    private final Logger logger = Logger.getLogger(Log.class.getName());

    private final String correlationId;
    private final String requestURI;
    private final String requestMethod;
    private final String logTypeCode;
    private final LogType logType;

    public String toJsonMessage() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            String failedConversionErrorLog = buildFailedConversionErrorLog();
            logger.debug(failedConversionErrorLog);
            return failedConversionErrorLog;
        }
    }

    private String buildFailedConversionErrorLog() {
        StringJoiner joiner = new StringJoiner(",\n", "{\n", "\n}");
        joiner.add(buildJsonField("correlationId", correlationId));
        joiner.add(buildJsonField("requestURI", requestURI));
        joiner.add(buildJsonField("requestMethod", requestMethod));
        joiner.add(buildJsonField("logTypeCode", "DESERIALIZATION_ERROR"));
        joiner.add(buildJsonField("logType", null));

        return joiner.toString();
    }

    private String buildJsonField(String fieldName, String fieldValue) {
        return String.format("\t\"%s\": \"%s\"", fieldName, fieldValue);
    }
}
