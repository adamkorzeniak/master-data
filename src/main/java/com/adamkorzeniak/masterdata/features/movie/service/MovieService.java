package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.features.movie.model.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<Movie> searchMovies(Map<String, String> allRequestParams);

    /**
     * Returns optional of movie for given id.
     * If movie not found empty optional
     */
    Optional<Movie> findMovieById(Long id);

    /**
     * Create movie in database.
     * Returns created movie
     */
    Movie addMovie(Movie movie);

    /**
     * Update movie for given id in database.
     * Returns updated movie
     */
    Movie updateMovie(Long id, Movie movie);

    /**
     * Delete movie for given id in database.
     */
    void deleteMovie(Long id);

    /**
     * Returns if movie exists in database
     */
    boolean isMovieExist(Long id);
}
