package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.movie.model.Movie;

public interface MovieService {

	List<Movie> searchMovies(Map<String, String> allRequestParams);

	/**
	 * Returns optional of movie for given id. 
	 * If movie not found empty optional
	 * 
	 * @param id Must not be null
	 * 
	 * @return Optional of movie for given id
	 */
	Optional<Movie> findMovieById(Long id);

	/**
	 * Create movie in database.
	 * Returns created movie
	 * 
	 * @param movie Must not be null
	 * 
	 * @return Created movie
	 */
	Movie addMovie(Movie movie);

	/**
	 * Update movie for given id in database.
	 * Returns updated movie
	 * 
	 * @param id Must not be null
	 * @param movie Must not be null
	 * 
	 * @return Updated movie
	 */
	Movie updateMovie(Long id, Movie movie);

	/**
	 * Delete movie for given id in database.
	 * 
	 * @param id Must not be null
	 */
	void deleteMovie(Long id);

	/**
	 * Returns if movie exists in database
	 * 
	 * @param id Must not be null
	 * 
	 * @return If movie exists
	 */
	boolean isMovieExist(Long id);
}
