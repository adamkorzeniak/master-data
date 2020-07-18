package com.adamkorzeniak.masterdata.api.auth.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users", catalog = "authentication")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotNull
    private Role role;

}
