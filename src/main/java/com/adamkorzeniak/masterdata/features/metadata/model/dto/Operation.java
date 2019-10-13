package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Operation {

    private String url;
    private String method;
    private String operationId;
    private String summary;
    private String description;
    @JsonIgnore
    private List<String> modules;
    private List<ModelSchema> uriParams;
    private List<ModelSchema> queryParams;
    private ModelSchema requestBody;
    private List<ModelSchema> responses;

    public Operation() {
        this.modules = new ArrayList<>();
        this.uriParams = new ArrayList<>();
        this.queryParams = new ArrayList<>();
        this.responses = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%s:%s - %s", method, url, operationId);
    }
}
