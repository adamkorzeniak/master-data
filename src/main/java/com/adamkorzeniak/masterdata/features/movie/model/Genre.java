package com.adamkorzeniak.masterdata.features.movie.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = {"id"})
@EqualsAndHashCode(of = "name")
@Entity
@Table(name = "genre", catalog = "movie")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name")
    private String name;

}
