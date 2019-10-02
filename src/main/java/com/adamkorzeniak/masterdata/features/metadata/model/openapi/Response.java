package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Response {

    private String description;
    private Map<String, RequestBodyContent> content;
}
