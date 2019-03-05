package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.movie.model.Genre;

public interface GenreService {

	List<Genre> searchGenres(Map<String, String> allRequestParams);

	/**
	 * Returns optional of genre for given id. 
	 * If genre not found empty optional
	 * 
	 * @param id Must not be null
	 * 
	 * @return Optional of genre for given id
	 */
	Optional<Genre> findGenreById(Long id);

	/**
	 * Create genre in database.
	 * Returns created genre
	 * 
	 * @param genre Must not be null
	 * 
	 * @return Created genre
	 */
	Genre addGenre(Genre genre);

	/**
	 * Update genre for given id in database.
	 * Returns updated genre
	 * 
	 * @param id Must not be null
	 * @param genre Must not be null
	 * 
	 * @return Updated genre
	 */
	Genre updateGenre(Long id, Genre genre);

	/**
	 * Delete genre for given id in database.
	 * 
	 * @param id Must not be null
	 */
	void deleteGenre(Long id);

	/**
	 * Returns if genre exists in database
	 * 
	 * @param id Must not be null
	 * 
	 * @return If genre exists
	 */
	boolean isGenreExist(Long id);

	Genre mergeGenres(Long genreId, Long targetGenreId);
}
