package com.adamkorzeniak.masterdata.features.location.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    private Long id;

    @Min(0)
    @Max(100)
    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review")
    private String review;

    @Column(name = "comment")
    private String comment;

    @Column(name = "review_date")
    private LocalDateTime reviewDate;

    @Column(name = "visited_date")
    private LocalDateTime visitedDate;
}
