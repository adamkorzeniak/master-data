package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenapiParameter {

    private String in;
    private String name;
    private String description;
    private Boolean required;
    private OpenapiSchemaComponent schema;

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
