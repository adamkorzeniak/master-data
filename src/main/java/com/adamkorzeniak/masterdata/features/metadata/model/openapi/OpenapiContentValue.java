package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties({"example"})
public class OpenapiContentValue {

    private OpenapiComponentReference schema;

    @Override
    public String toString() {
        return String.format("Schema: %s", schema);
    }
}
