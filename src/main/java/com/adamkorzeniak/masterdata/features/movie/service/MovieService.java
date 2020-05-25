package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.features.movie.model.Movie;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<Movie> searchMovies(Map<String, String> allRequestParams);

    Optional<Movie> findMovieById(Long id);

    Movie addMovie(Movie movie);

    Movie updateMovie(Long id, Movie movie);

    void deleteMovie(Long id);

    boolean isMovieExist(Long id);
}
