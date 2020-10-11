package com.adamkorzeniak.masterdata.api.features.goal.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "goal_element", catalog = "goals")
public class GoalElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "multiplier")
    private Double multiplier;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "goal_element_id")
    private Set<GoalElementEntry> goalElementEntries;

}
