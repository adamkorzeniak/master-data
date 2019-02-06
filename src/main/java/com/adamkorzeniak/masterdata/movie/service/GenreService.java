package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;

public interface GenreService {

	/**
	 * Returns list of all genres. 
	 * 
	 * @return  List of all genres
	 */
	List<Genre> findAllGenres();

	/**
	 * Returns genre for given id. 
	 * If genre not found returns null
	 * 
	 * @return  Genre for given id
	 */
	Optional<Genre> findGenreById(Long id);

	/**
	 * Create genre in database. Return created genre
	 * 
	 * @return  Created genre
	 */
	Genre addGenre(Genre genre);

	/**
	 * Update genre for given in database. Return updated genre
	 * 
	 * @return  Updated genre
	 */
	Genre updateGenre(Long id, Genre genre);

	/**
	 * Delete genre for given in database.
	 * 
	 */
	void deleteGenre(Long id);

	/**
	 * Returns if genre exists
	 * 
	 * @return  If genre exists
	 */
	boolean isGenreExist(Long id);

	/**
	 * Returns list of genres which name contains provided string. 
	 * 
	 * @return 	List of genres which name contains provided string
	 */
	List<Genre> findGenresByName(String name);

	List<Genre> searchGenres(Map<String, String> allRequestParams);
}
