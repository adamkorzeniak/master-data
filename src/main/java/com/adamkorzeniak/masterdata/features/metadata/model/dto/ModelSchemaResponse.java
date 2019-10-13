package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ModelSchemaResponse {

    private String name;
    private String httpStatus;
    private String description;
    private Boolean required;
    private Boolean isArray;
    private String type;
    private String format;
    private String example;
    private Integer minLength;
    private Integer maxLength;
    private Integer minimum;
    private Integer maximum;
    private Boolean readOnly;
    private Boolean writeOnly;
    private List<String> enums;
    private List<ModelSchemaResponse> parameters;
    @JsonIgnore
    private SchemaType schemaType;

    public void addParameter(ModelSchemaResponse parameter) {
        if (parameters == null) {
            parameters = new ArrayList<>();
        }
        parameters.add(parameter);
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
