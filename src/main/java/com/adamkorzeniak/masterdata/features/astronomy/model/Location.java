package com.adamkorzeniak.masterdata.features.astronomy.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "location", catalog = "astronomy")
public class Location extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "comment")
    private String comment;

    @Column(name = "city")
    private String city;

    @Min(-180)
    @Max(180)
    @Column(name = "lattitude")
    private Double lattitude;

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
    private Set<Observation> observations;
}
