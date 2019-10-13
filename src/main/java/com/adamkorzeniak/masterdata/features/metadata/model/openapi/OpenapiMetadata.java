package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(value = {"info", "openapi", "security", "servers"})
public class OpenapiMetadata {

    private List<OpenapiTag> tags;
    private Map<String, Map<String, OpenapiPath>> paths;
    private OpenapiComponent components;

    @Override
    public String toString() {
        return String.format("Modules: %s", tags);
    }
}
