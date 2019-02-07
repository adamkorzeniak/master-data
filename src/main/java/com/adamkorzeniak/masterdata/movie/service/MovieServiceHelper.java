package com.adamkorzeniak.masterdata.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.adamkorzeniak.masterdata.exception.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.model.dto.MovieDTO;
import com.adamkorzeniak.masterdata.shared.FilterParameter;

public class MovieServiceHelper {

	private static final String TITLE_FIELD = "title";
	private static final String YEAR_FIELD = "year";
	private static final String DURATION_FIELD = "duration";
	private static final String DESCRIPTION_FIELD = "description";
	private static final String RATING_FIELD = "rating";
	private static final String WATCH_PRIORITY_FIELD = "watchPriority";
	private static final String REVIEW_FIELD = "review";
	private static final String PLOT_SUMMARY_FIELD = "plotSummary";

	private MovieServiceHelper() {
	}

	public static Movie convertToEntity(MovieDTO dto) {
		Movie entity = new Movie();
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setYear(dto.getYear());
		entity.setDuration(dto.getDuration());
		entity.setRating(dto.getRating());
		entity.setWatchPriority(dto.getWatchPriority());
		entity.setDescription(dto.getDescription());
		entity.setReview(dto.getReview());
		entity.setPlotSummary(dto.getPlotSummary());
		entity.setReviewDate(dto.getReviewDate());
		entity.setGenres(
				dto.getGenres().stream().map(GenreServiceHelper::convertToEntity).collect(Collectors.toList()));

		return entity;
	}

	public static MovieDTO convertToDTO(Movie entity) {
		MovieDTO dto = new MovieDTO();
		dto.setId(entity.getId());
		dto.setTitle(entity.getTitle());
		dto.setYear(entity.getYear());
		dto.setDuration(entity.getDuration());
		dto.setRating(entity.getRating());
		dto.setWatchPriority(entity.getWatchPriority());
		dto.setDescription(entity.getDescription());
		dto.setReview(entity.getReview());
		dto.setPlotSummary(entity.getPlotSummary());
		dto.setReviewDate(entity.getReviewDate());
		dto.setGenres(entity.getGenres().stream().map(GenreServiceHelper::convertToDTO).collect(Collectors.toList()));

		return dto;
	}

	public static List<FilterParameter> buildFilters(Map<String, String> map) {

		List<FilterParameter> filters = new ArrayList<>();

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			FilterParameter filter = new FilterParameter((String) pair.getKey(), (String) pair.getValue());
			validateAndFix(filter);
			filters.add(filter);
		}

		return filters;
	}

	private static void validateAndFix(FilterParameter filter) {
		switch (filter.getFunction()) {
		case SEARCH:
			if (!Arrays.asList(TITLE_FIELD, DESCRIPTION_FIELD, REVIEW_FIELD, PLOT_SUMMARY_FIELD)
					.contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		case MATCH:
			if (!Arrays.asList(TITLE_FIELD).contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		case MIN:
		case MAX:
			if (!Arrays.asList(YEAR_FIELD, DURATION_FIELD, RATING_FIELD, WATCH_PRIORITY_FIELD)
					.contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		case ORDER_ASC:
		case ORDER_DESC:
			if (!Arrays.asList(TITLE_FIELD, YEAR_FIELD, DURATION_FIELD, RATING_FIELD, WATCH_PRIORITY_FIELD)
					.contains(filter.getField())) {
				throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
			}
			break;
		}

	}
}
