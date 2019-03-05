package com.adamkorzeniak.masterdata.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFilterServiceImpl;
import com.adamkorzeniak.masterdata.exception.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.FilterNotSupportedException;
import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.dto.GenreDTO;

public class GenreServiceHelper {

	private GenreServiceHelper() {
	}

	public static Genre convertToEntity(GenreDTO dto) {
		Genre entity = new Genre();
		entity.setId(dto.getId());
		entity.setName(dto.getName());

		return entity;
	}

	public static GenreDTO convertToDTO(Genre entity) {
		GenreDTO dto = new GenreDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());

		return dto;
	}
}
