package com.adamkorzeniak.masterdata.entity.astronomy.model;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"location"})
@Entity
@Table(name = "object", catalog = "astronomy")
public class Object extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "name_en")
    private String nameEn;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "messier")
    private Integer messier;

    @NotEmpty
    @Column(name = "ngc")
    private Integer ngc;

    @NotEmpty
    @Column(name = "star")
    private String star;

    @NotEmpty
    @Column(name = "others")
    private String others;

    @NotEmpty
    @Column(name = "type")
    private String type;

    @NotEmpty
    @Column(name = "magnitude_min")
    private Double magnitudeMin;

    @NotEmpty
    @Column(name = "magnitude_max")
    private Double magnitudeMax;

    @NotEmpty
    @Column(name = "app_size_1")
    private Double appSize1;

    @NotEmpty
    @Column(name = "app_size_2")
    private Double appSize2;

    @NotEmpty
    @Column(name = "details")
    private String details;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "object")
    private ObjectLocation location;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
    private Set<ObservationItem> items;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
    private Set<ObservationPlan> plans;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "object")
    private Set<Source> sources;
}
