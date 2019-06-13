package com.adamkorzeniak.masterdata.features.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;

public interface GenreService {

	List<Genre> searchGenres(Map<String, String> allRequestParams);

	/**
	 * 
	 * Returns optional of genre for given id. 
	 * If genre not found empty optional
	 * 
	 */
	Optional<Genre> findGenreById(Long id);

	/**
	 * 
	 * Create genre in database.
	 * Returns created genre
	 * 
	 */
	Genre addGenre(Genre genre);

	/**
	 * 
	 * Update genre for given id in database.
	 * Returns updated genre
	 * 
	 */
	Genre updateGenre(Long id, Genre genre);

	/**
	 * 
	 * Delete genre for given id in database.
	 * 
	 */
	void deleteGenre(Long id);

	/**
	 * 
	 * Returns if genre exists in database
	 * 
	 */
	boolean isGenreExist(Long id);

	Genre mergeGenres(Long genreId, Long targetGenreId);
}
