package com.adamkorzeniak.masterdata.entity.error.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
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
public class Error extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "error_id")
    private String errorId;

    @NotEmpty
    @Column(name = "app_id")
    private String appId;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "details")
    private String details;

    @NotEmpty
    @Column(name = "status")
    private String status;

    @NotEmpty
    @Column(name = "url")
    private String url;

    @NotEmpty
    @Column(name = "stack")
    private String stack;

    @Column(name = "time")
    private Long time;
}
