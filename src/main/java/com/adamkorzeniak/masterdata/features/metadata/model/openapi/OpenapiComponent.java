package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(value = {"securitySchemes", "examples"})
public class OpenapiComponent {

    private Map<String, OpenapiSchemaComponent> schemas;
    private Map<String, OpenapiContentComponent> requestBodies;
    private Map<String, OpenapiContentComponent> responses;

    @Override
    public String toString() {
        return String.format("schemas: %s, request bodies: %s, responses: %s",
                schemas.keySet(),
                requestBodies.keySet(),
                responses.keySet());
    }
}
