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
@Table(name = "goal_group", catalog = "goals")
public class GoalGroup {

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
    @Column(name = "significance")
    private Double significance;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Set<Goal> goals;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private Set<GoalGroupConfiguration> goalGroupConfigurations;

}
