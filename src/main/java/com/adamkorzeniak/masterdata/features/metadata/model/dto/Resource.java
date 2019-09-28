package com.adamkorzeniak.masterdata.features.metadata.model.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Resource {

    private String url;
    private List<OperationResponse> operations;
}
