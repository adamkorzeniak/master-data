package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class ComponentSchema {

    private String description;
    private String type;
    private String format;
    private ComponentReference items;
    private List<String> required;
    private String example;
    private Integer minLength;
    private Integer maxLength;
    private Integer minimum;
    private Integer maximum;
    @JsonProperty("enum")
    private List<String> enums;
    private Boolean readOnly;
    private Boolean writeOnly;
    private Map<String, ComponentSchema> properties;
}
