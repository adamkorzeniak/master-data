package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Operation {

    private String summary;
    private String operationId;
    private String description;
    private List<String> tags;
    private ComponentReference requestBody;
    private Map<String, ComponentReference> responses;
    private List<Parameter> parameters;
}
