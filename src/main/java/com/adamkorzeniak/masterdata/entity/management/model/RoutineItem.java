package com.adamkorzeniak.masterdata.entity.management.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode
@Entity
@Table(name = "routine_item", catalog = "management")
public class RoutineItem extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotEmpty
    @Column(name = "importance")
    private Integer importance;

}
