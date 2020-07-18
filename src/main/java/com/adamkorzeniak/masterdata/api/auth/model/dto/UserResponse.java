package com.adamkorzeniak.masterdata.api.auth.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserResponse {

    @NotEmpty
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String role;
}
