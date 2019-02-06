package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.movie.model.Movie;

public interface MovieService {

	/**
	 * Returns list of all movies. 
	 * 
	 * @return  List of all movies
	 */
	List<Movie> findAllMovies();

	/**
	 * Returns movie for given id. 
	 * If movie not found returns null
	 * 
	 * @return  Movie for given id
	 */
	Movie findMovieById(Long id);

	/**
	 * Create movie in database. Return created movie
	 * 
	 * @return  Created movie
	 */
	Movie addMovie(Movie movie);

	/**
	 * Update movie for given in database. Return updated movie
	 * 
	 * @return  Updated movie
	 */
	Movie updateMovie(Long id, Movie movie);
	
	/**
	 * Delete movie for given in database.
	 * 
	 */
	void deleteMovie(Long id);

	/**
	 * Returns if movie exists
	 * 
	 * @return  If movie exists
	 */
	boolean isMovieExist(Long id);

	List<Movie> searchMovies(Map<String,String> filter);

//	Movie addGenreToMovie(Long movieId, Long genreId);
//
//	Movie removeGenreFromMovie(Long movieId, Long genreId);
}
