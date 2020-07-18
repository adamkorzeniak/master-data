package com.adamkorzeniak.masterdata.entity.location.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "review", catalog = "location")
public class PlaceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    @Min(0)
    @Max(100)
    @Column(name = "rating")
    private Integer rating;

    @NotEmpty
    @Column(name = "review")
    private String review;

    @NotEmpty
    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "visited_date")
    private LocalDateTime visitedDate;
}
