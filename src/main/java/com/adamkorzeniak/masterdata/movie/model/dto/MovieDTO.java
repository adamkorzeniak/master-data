package com.adamkorzeniak.masterdata.movie.model.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString 
public class MovieDTO {
	
	private Long id;

	@NotBlank
	private String title;

	@Min(1800)	@Max(2999)
	private Integer year;

	@Min(0)	@Max(2999)
	private Integer duration;

	@Min(0) @Max(10)
	private Integer rating;

	@Min(0)	@Max(10)
	private Integer watchPriority;

	private String description;

	private String review;

	private String plotSummary;

	private LocalDate reviewDate;
	
	private List<GenreDTO> genres;
}
