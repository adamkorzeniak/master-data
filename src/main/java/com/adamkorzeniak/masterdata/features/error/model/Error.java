package com.adamkorzeniak.masterdata.features.error.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "error__errors")
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "error_id")
    private String errorId;

    @NotBlank
    @Column(name = "app_id")
    private String appId;

    @Column(name = "name")
    private String name;

    @Column(name = "details")
    private String details;

    @Column(name = "status")
    private String status;

    @Column(name = "url")
    private String url;

    @Column(name = "stack")
    private String stack;

    @Column(name = "time")
    private Long time;
}
