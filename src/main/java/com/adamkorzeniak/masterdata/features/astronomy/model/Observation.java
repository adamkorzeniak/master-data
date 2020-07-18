package com.adamkorzeniak.masterdata.features.astronomy.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"location"})
@Entity
@Table(name = "observation", catalog = "astronomy")
public class Observation extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    @NotEmpty
    @Column(name = "notes")
    private String notes;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "observation_id")
    private Set<ObservationItem> items;
}