package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class OpenapiPath {

    private String operationId;
    private String summary;
    private String description;
    private List<String> tags;
    private List<OpenapiParameter> parameters;
    private OpenapiComponentReference requestBody;
    private Map<String, OpenapiComponentReference> responses;

    @Override
    public String toString() {
        return String.format("%s: %s", operationId, description);
    }
}
