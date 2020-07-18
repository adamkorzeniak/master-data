package com.adamkorzeniak.masterdata.features.programming.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"items"})
@Entity
@Table(name = "checklist_group", catalog = "programming")
public class ChecklistGroup extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "group")
    private String group;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    private Set<ChecklistItem> items;

}