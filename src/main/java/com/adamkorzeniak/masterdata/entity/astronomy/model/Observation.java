package com.adamkorzeniak.masterdata.entity.astronomy.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"items"})
@Entity
@Table(name = "observation", catalog = "astronomy")
public class Observation extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    @JsonBackReference
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