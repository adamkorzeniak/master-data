package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComponentSchemaProperty {

    private String description;
    private String type;
    private String format;
    private ComponentReference items;
    private String example;
    private Integer minLength;
    private Integer maxLength;
    private Integer minimum;
    private Integer maximum;
    @JsonProperty("enum")
    private List<String> enums;
    private Boolean readOnly;
    private Boolean writeOnly;
}
