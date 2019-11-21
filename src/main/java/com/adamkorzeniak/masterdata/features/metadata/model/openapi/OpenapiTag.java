package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpenapiTag {
    private String name;
    private String description;

    @Override
    public String toString() {
        return String.format("%s: %s", name, description);
    }
}
