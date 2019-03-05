package com.adamkorzeniak.masterdata.movie.service;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.dto.GenreDTO;

public class GenreServiceHelper {

	private GenreServiceHelper() {}

	/**
	 * Converts Genre DTO to Entity.
	 * 
	 * @param dto Must not be null.
	 * 
	 * @return Genre entity
	 */
	public static Genre convertToEntity(GenreDTO dto) {
		Genre entity = new Genre();
		entity.setId(dto.getId());
		entity.setName(dto.getName());

		return entity;
	}

	/**
	 * Converts Genre Entity to DTO.
	 * 
	 * @param entity Must not be null.
	 * 
	 * @return Genre DTO
	 */
	public static GenreDTO convertToDTO(Genre entity) {
		GenreDTO dto = new GenreDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());

		return dto;
	}
}
