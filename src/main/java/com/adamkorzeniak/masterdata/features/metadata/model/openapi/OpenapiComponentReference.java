package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenapiComponentReference {

    @JsonProperty("$ref")
    private String ref;

    @Override
    public String toString() {
        return String.format("$ref: %s", ref);
    }
}
