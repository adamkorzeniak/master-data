package com.adamkorzeniak.masterdata.features.movie.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MovieDTO {

    private Long id;

    @NotBlank
    @NotNull
    private String title;

    @NotBlank
    private String originalTitle;

    @Min(1800)
    @Max(2999)
    @NotNull
    private Integer year;

    @Min(0)
    @Max(1000)
    @NotNull
    private Integer duration;

    @NotBlank
    private String directors;

    @NotBlank
    private String productionCountries;

    @Min(0)
    @Max(10)
    private Float filmwebRating;

    @Min(0)
    private Integer filmwebRatingCount;

    @Min(0)
    private Integer filmwebTopRanking;

    @Min(0)
    @Max(10)
    private Integer rating;

    @Min(0)
    @Max(5)
    private Integer watchPriority;

    private String filmPlot;

    private String description;

    private String review;

    private String plotSummary;

    private LocalDate reviewDate;

    private List<GenreDTO> genres;
}
