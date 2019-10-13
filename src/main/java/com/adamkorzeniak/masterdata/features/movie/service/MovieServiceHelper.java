package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MovieServiceHelper {

    private MovieServiceHelper() {
    }

    /**
     * Converts Movie DTO to Entity.
     */
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
        if (dto.getGenres() == null) {
            dto.setGenres(new ArrayList<>());
        }
        entity.setGenres(
            dto.getGenres().stream()
                .map(GenreServiceHelper::convertToEntity)
                .collect(Collectors.toList()));

        return entity;
    }

    /**
     * Converts Movie Entity to DTO.
     */
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
        if (entity.getGenres() == null) {
            entity.setGenres(new ArrayList<>());
        }
        dto.setGenres(
            entity.getGenres().stream()
                .map(GenreServiceHelper::convertToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
