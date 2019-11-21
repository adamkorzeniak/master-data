package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OpenapiContentComponent {

    private String description;
    private Boolean required;
    private Map<String, OpenapiContentValue> content;

    @Override
    public String toString() {
        return String.format("%s: %s",
                content == null ? "No content" : content.keySet(),
                description);
    }
}
