package com.adamkorzeniak.masterdata.features.programming.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode
@Entity
@Table(name = "intellij_tip", catalog = "programming")
public class IntellijTip extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "action")
    private String action;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotEmpty
    @Column(name = "category")
    private String category;

}
