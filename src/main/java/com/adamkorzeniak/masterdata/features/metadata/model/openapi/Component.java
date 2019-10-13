package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(value = {"securitySchemes", "examples"})
public class Component {

    private Map<String, ComponentSchema> schemas;
    private Map<String, Body> requestBodies;
    private Map<String, Body> responses;
}
