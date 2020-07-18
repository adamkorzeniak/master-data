package com.adamkorzeniak.masterdata.api.auth.model.dto;

import com.adamkorzeniak.masterdata.annotation.EnumValidator;
import com.adamkorzeniak.masterdata.api.auth.model.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UserRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @EnumValidator(enumClazz = Role.class)
    private String role;
}
