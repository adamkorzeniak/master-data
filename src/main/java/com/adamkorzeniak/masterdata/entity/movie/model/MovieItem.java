package com.adamkorzeniak.masterdata.entity.movie.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "item", catalog = "book")
public class MovieItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Column(name = "language")
    private String language;

    @Column(name = "location")
    private String location;
}
