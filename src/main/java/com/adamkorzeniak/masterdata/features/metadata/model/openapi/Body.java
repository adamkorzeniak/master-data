package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Body {

    private String description;
    private Boolean required;
    private Map<String, BodyContent> content;
}
