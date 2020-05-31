package com.adamkorzeniak.masterdata.features.book.model;

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
@Table(name = "review", catalog = "book")
public class BookReview {

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

    @Column(name = "reading_start")
    private LocalDateTime readingStart;

    @Column(name = "reading_end")
    private LocalDateTime readingEnd;
}
