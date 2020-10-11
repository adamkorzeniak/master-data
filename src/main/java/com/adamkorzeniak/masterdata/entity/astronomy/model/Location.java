package com.adamkorzeniak.masterdata.entity.astronomy.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"observations"})

@Entity
@Table(name = "location", catalog = "astronomy")
public class Location extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "comment")
    private String comment;

    @NotEmpty
    @Column(name = "city")
    private String city;

    @Min(-180)
    @Max(180)
    @Column(name = "latitude")
    private Double latitude;

    @Min(-90)
    @Max(90)
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "visited")
    private boolean visited;

    @Min(0)
    @Max(100)
    @Column(name = "rating")
    private Integer rating;

    @Min(0)
    @Max(1000)
    @Column(name = "light_ratio")
    private Double lightRatio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "location")
    @JsonManagedReference
    private Set<Observation> observations;
}
