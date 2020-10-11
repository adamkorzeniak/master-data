package com.adamkorzeniak.masterdata.entity.astronomy.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"skyObject"})
@Entity
@Table(name = "sky_object_location", catalog = "astronomy")
public class SkyObjectLocation extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "object_id")
    private SkyObject skyObject;

    @NotEmpty
    @Column(name = "constellation_en")
    private String constellationEn;

    @NotEmpty
    @Column(name = "constellation")
    private String constellation;

    @Min(0)
    @Max(24)
    @Column(name = "right_ascension_hours")
    private Integer rightAscensionHours;

    @Min(-60)
    @Max(60)
    @Column(name = "right_ascension_minutes")
    private Integer rightAscensionMinutes;

    @Min(-60)
    @Max(60)
    @Column(name = "right_ascension_seconds")
    private Double rightAscensionSeconds;

    @Min(-90)
    @Max(90)
    @Column(name = "declination_degrees")
    private Integer declinationDegrees;

    @Min(-60)
    @Max(60)
    @Column(name = "declination_minutes")
    private Integer declinationMinutes;

    @Min(-60)
    @Max(60)
    @Column(name = "declination_seconds")
    private Double declinationSeconds;

    @Min(0)
    @Column(name = "distance")
    private Double distance;
}
