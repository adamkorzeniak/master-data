package com.adamkorzeniak.masterdata.features.movie.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Movie")
public class MovieController {

    private static final String MOVIE_RESOURCE_NAME = "Movie";

    private final MovieService movieService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public MovieController(MovieService movieService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.movieService = movieService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of movies with 200 OK.
     * <p>
     * If there are no movies it returns empty list with 204 No Content
     */
    @GetMapping("/movies")
    public ResponseEntity<List<Map<String, Object>>> findMovies(@RequestParam Map<String, String> allRequestParams) {
        List<Movie> movies = movieService.searchMovies(allRequestParams);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(movies, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns movie with given id with 200 OK.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<Movie> findMovieById(@PathVariable("movieId") Long movieId) {
        Optional<Movie> movie = movieService.findMovieById(movieId);
        if (movie.isEmpty()) {
            throw new NotFoundException(MOVIE_RESOURCE_NAME, movieId);
        }
        return new ResponseEntity<>(movie.get(), HttpStatus.OK);
    }

    /**
     * Creates a movie in database.
     * Returns created movie with 201 Created.
     * <p>
     * If provided movie data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/movies")
    public ResponseEntity<Movie> addMovie(@RequestBody @Valid Movie movie) {
        Movie newMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    /**
     * Updates a movie with given id in database. Returns updated movie with 200 OK.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided movie data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/movies/{movieId}")
    public ResponseEntity<Movie> updateMovie(@RequestBody @Valid Movie movie, @PathVariable Long movieId) {
        boolean exists = movieService.isMovieExist(movieId);
        if (!exists) {
            throw new NotFoundException(MOVIE_RESOURCE_NAME, movieId);
        }
        Movie newMovie = movieService.updateMovie(movieId, movie);
        return new ResponseEntity<>(newMovie, HttpStatus.OK);
    }

    /**
     * Deletes a movie with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/movies/{movieId}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
        boolean exists = movieService.isMovieExist(movieId);
        if (!exists) {
            throw new NotFoundException(MOVIE_RESOURCE_NAME, movieId);
        }
        movieService.deleteMovie(movieId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
