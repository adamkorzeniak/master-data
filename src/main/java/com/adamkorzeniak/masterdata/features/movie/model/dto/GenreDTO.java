package com.adamkorzeniak.masterdata.features.movie.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class GenreDTO {

	private Long id;
	@NotBlank
	private String name;

}
