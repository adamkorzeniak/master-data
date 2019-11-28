package com.adamkorzeniak.masterdata.features.error.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ErrorDTO {

    private Long id;

    @NotEmpty
    private String errorId;

    @NotEmpty
    private String appId;

    private String name;

    private String details;

    private String status;

    private String url;

    private String stack;

    private Long time;
}
