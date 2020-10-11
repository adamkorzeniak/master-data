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
@Table(name = "goal_rating_conf", catalog = "goals")
public class GoalRatingConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotNull
    @Column(name = "worstValue")
    private Double worstValue;

    @NotNull
    @Column(name = "badValue")
    private Double badValue;

    @NotNull
    @Column(name = "goodValue")
    private Double goodValue;

    @NotNull
    @Column(name = "bestValue")
    private Double bestValue;

    @NotEmpty
    @Column(name = "validFrom")
    private LocalDate validFrom;

    @NotEmpty
    @Column(name = "validTo")
    private LocalDate validTo;
}
