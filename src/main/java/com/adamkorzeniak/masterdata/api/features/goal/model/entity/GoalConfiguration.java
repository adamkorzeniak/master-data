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
@Table(name = "goal_conf", catalog = "goals")
public class GoalConfiguration implements ValidDayPeriod{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "validFrom")
    private LocalDate validFrom;

    @NotNull
    @Column(name = "validTo")
    private LocalDate validTo;
}
