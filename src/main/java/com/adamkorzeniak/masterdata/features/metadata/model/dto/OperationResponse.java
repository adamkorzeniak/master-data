package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OperationResponse {

    private String url;
    private String method;
    private String operationId;
    private String summary;
    private String description;
    @JsonIgnore
    private List<String> modules;
    private List<ModelSchemaResponse> uriParams;
    private List<ModelSchemaResponse> queryParams;
    private ModelSchemaResponse requestBody;
    private List<ModelSchemaResponse> responses;

    @Override
    public String toString() {
        return String.format("%s:%s - %s", method, url, operationId);
    }
}
