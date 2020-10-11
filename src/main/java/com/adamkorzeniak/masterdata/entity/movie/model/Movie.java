package com.adamkorzeniak.masterdata.entity.movie.model;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(exclude = {"genres", "reviews", "plans"})
@Entity
@Table(name = "movie", catalog = "movie")
public class Movie extends DatabaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @NotEmpty
    @Column(name = "title")
    private String title;

    @Min(1800)
    @Max(2999)
    @NotNull
    @Column(name = "year")
    private Integer year;

    @Min(0)
    @Max(1000)
    @NotNull
    @Column(name = "duration")
    private Integer duration;

    @Column(name = "description")
    private String description;

    @Column(name = "plot_summary")
    private String plotSummary;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "movie_genre", catalog = "movie", joinColumns = @JoinColumn(name = "movie_id"), inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    private Set<MovieReview> reviews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    private Set<MovieWatchPlan> plans;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "movie_id")
    private Set<MovieItem> items;
}
