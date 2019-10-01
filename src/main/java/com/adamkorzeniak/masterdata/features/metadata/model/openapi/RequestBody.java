package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class RequestBody {

    private String description;
    private Boolean required;
    private Map<String, RequestBodyContent> content;
}
