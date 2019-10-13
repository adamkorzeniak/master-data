package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Path {

    private String operationId;
    private String summary;
    private String description;
    private List<String> tags;
    private List<Parameter> parameters;
    private ComponentReference requestBody;
    private Map<String, ComponentReference> responses;
}
