package com.adamkorzeniak.masterdata.features.movie.controller;

import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;
import com.adamkorzeniak.masterdata.features.movie.service.MovieService;
import com.adamkorzeniak.masterdata.features.movie.service.MovieServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v0/Movie")
public class MovieController {

    private static final String MOVIE_RESOURCE_NAME = "Movie";

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Returns list of movies with 200 OK.
     * <p>
     * If there are no movies it returns empty list with 204 No Content
     *
     * @return List all movies
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieDTO>> findMovies(@RequestParam Map<String, String> allRequestParams) {

        List<MovieDTO> dtos = movieService.searchMovies(allRequestParams).stream().map(MovieServiceHelper::convertToDTO)
            .collect(Collectors.toList());
        if (dtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Returns movie with given id with 200 OK.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not
     * Found
     *
     * @param movieId - Id of movie
     * @return Movie for given id
     */
    @GetMapping("/movies/{movieId}")
    public ResponseEntity<MovieDTO> findMovieById(@PathVariable("movieId") Long movieId) {
        Optional<Movie> result = movieService.findMovieById(movieId);
        if (result.isEmpty()) {
            throw new NotFoundException(MOVIE_RESOURCE_NAME, movieId);
        }
        MovieDTO dto = MovieServiceHelper.convertToDTO(result.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Creates a movie in database.
     * Returns created movie with 201 Created.
     * <p>
     * If provided movie data is invalid it returns 400 Bad Request.
     *
     * @param movie - Movie to be created
     * @return Created movie
     */
    @PostMapping("/movies")
    public ResponseEntity<MovieDTO> addMovie(@RequestBody @Valid MovieDTO dto) {
        Movie movie = MovieServiceHelper.convertToEntity(dto);
        Movie newMovie = movieService.addMovie(movie);
        return new ResponseEntity<>(MovieServiceHelper.convertToDTO(newMovie), HttpStatus.CREATED);
    }

    /**
     * Updates a movie with given id in database. Returns updated movie with 200 OK.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not
     * Found
     * <p>
     * If provided movie data is invalid it returns 400 Bad Request.
     *
     * @param movieId - Id of movie
     * @param movie   - Movie to be updated
     * @return Updated movie
     */
    @PutMapping("/movies/{movieId}")
    public ResponseEntity<MovieDTO> updateMovie(@RequestBody @Valid MovieDTO dto, @PathVariable Long movieId) {
        boolean exists = movieService.isMovieExist(movieId);
        if (!exists) {
            throw new NotFoundException(MOVIE_RESOURCE_NAME, movieId);
        }
        Movie movie = MovieServiceHelper.convertToEntity(dto);
        Movie newMovie = movieService.updateMovie(movieId, movie);
        return new ResponseEntity<>(MovieServiceHelper.convertToDTO(newMovie), HttpStatus.OK);
    }

    /**
     * Deletes a movie with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If movie with given id does not exist it returns error response with 404 Not
     * Found
     *
     * @param movieId - Id of movie
     * @return Empty response
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
