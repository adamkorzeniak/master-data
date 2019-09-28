package com.adamkorzeniak.masterdata.features.user.model.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

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
