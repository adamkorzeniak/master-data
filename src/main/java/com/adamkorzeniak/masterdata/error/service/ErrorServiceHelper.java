package com.adamkorzeniak.masterdata.error.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.exception.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.FilterNotSupportedException;
import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.dto.GenreDTO;

public class ErrorServiceHelper {

	private static final String TITLE_FIELD = "title";
	private static final String DESCRIPTION_FIELD = "description";

	private ErrorServiceHelper() {}

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

	public static List<SearchFilter> buildFilters(Map<String, String> map) {

		List<SearchFilter> filters = new ArrayList<>();

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			SearchFilter filter = new SearchFilter((String) pair.getKey(), (String) pair.getValue());
			validateAndFix(filter);
			filters.add(filter);
		}
		return filters;
	}

	private static void validateAndFix(SearchFilter filter) {
		switch (filter.getFunction()) {
		case MIN:
		case MAX:
		case EXIST:
			throw new FilterNotSupportedException(filter.getFunction());
		case MATCH:
			if (!Arrays.asList(TITLE_FIELD).contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		case SEARCH:
		case ORDER_ASC:
		case ORDER_DESC:
			if (!Arrays.asList(TITLE_FIELD, DESCRIPTION_FIELD).contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		}
	}
}
