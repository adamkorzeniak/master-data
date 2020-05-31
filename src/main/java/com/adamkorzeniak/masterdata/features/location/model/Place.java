package com.adamkorzeniak.masterdata.features.location.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode
@Entity
@Table(name = "place", catalog = "movie")
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "name_en")
    private String name_en;

    @NotEmpty
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "country_id")
    private Country country;

}
