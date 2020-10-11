package com.adamkorzeniak.masterdata.api.features.goal.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "goal_element_entry", catalog = "goals")
public class GoalElementEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "value")
    private Double value;

    @NotNull
    @Column(name = "entryDate")
    private LocalDate entryDate;

}
