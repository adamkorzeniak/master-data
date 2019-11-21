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
public class ModelSchema {

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
    private List<ModelSchema> parameters;
    @JsonIgnore
    private SchemaType schemaType;

    public ModelSchema() {
        this.parameters = new ArrayList<>();
        this.enums = new ArrayList<>();
    }

    public void addParameter(ModelSchema parameter) {
        parameters.add(parameter);
    }

    @Override
    public String toString() {
        switch (schemaType) {
            case REQUEST_BODY:
                return String.format("Request body: %s", description);
            case RESPONSE:
                return String.format("Response status %s: %s", httpStatus, description);
            default:
                return String.format("%s %s: %s", schemaType, name, description);
        }
    }
}
