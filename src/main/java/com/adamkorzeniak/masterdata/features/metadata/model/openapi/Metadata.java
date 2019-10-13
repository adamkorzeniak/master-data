package com.adamkorzeniak.masterdata.features.metadata.model.openapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(value = {"info", "openapi", "security", "servers"})
public class Metadata {

    private List<Tag> tags;
    private Map<String, Map<String, Path>> paths;
    private Component components;
}
