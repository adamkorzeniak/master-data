package com.adamkorzeniak.masterdata.features.error.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "errors", catalog = "error")
public class Error {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "error_id")
    private String errorId;

    @NotEmpty
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
