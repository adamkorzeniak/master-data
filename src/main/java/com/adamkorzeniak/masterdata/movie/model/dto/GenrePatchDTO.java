package com.adamkorzeniak.masterdata.movie.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GenrePatchDTO {
	
	private String op;
	private Long targetGenreId;

}
