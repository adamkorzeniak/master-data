package com.adamkorzeniak.masterdata.features.location.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "country", catalog = "location")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Column(name = "name_en")
    private String name_em;

    @NotEmpty
    @Size(min = 2, max = 3)
    @Column(name = "code")
    private String code;
}
