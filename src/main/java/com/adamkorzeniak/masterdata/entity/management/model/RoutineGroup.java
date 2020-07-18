package com.adamkorzeniak.masterdata.entity.management.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"items"})
@Entity
@Table(name = "routine_group", catalog = "management")
public class RoutineGroup extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "group_id")
    private Set<RoutineItem> items;

}