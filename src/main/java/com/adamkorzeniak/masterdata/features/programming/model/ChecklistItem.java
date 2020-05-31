package com.adamkorzeniak.masterdata.features.programming.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode
@Entity
@Table(name = "checklist_item", catalog = "programming")
public class ChecklistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "order")
    private Integer order;

    @NotEmpty
    @Column(name = "title")
    private String title;

    @NotEmpty
    @Column(name = "description")
    private String description;

}
