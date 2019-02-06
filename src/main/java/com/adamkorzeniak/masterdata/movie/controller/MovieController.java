package com.adamkorzeniak.masterdata.movie.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.exception.NotFoundException;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.service.GenreService;
import com.adamkorzeniak.masterdata.movie.service.MovieService;

@RestController
@RequestMapping("/Movie/api/v0")
public class MovieController {
	
	@Autowired
	private MovieService movieService;
	
	@Autowired
	private GenreService genreService;
	
	/**
	 * Returns list of all movies with 200 OK. 
	 * <p>
	 * If there are no movies it returns empty list with 204 No Content
	 * 
	 * @return  List of all movies
	 */
	@GetMapping("/movies")
	public ResponseEntity<List<Movie>> findAllMovies() {
		List<Movie> movies = movieService.findAllMovies();
		if (movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	@GetMapping("/movies/search")
	public ResponseEntity<List<Movie>> findMovies(
			@RequestParam Map<String,String> allRequestParams
			) {
		
		List<Movie> movies = movieService.searchMovies(allRequestParams);
		if (movies.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(movies, HttpStatus.OK);
	}
	
	/**
	 * Returns movie with given id with 200 OK. 
	 * <p>
	 * If movie with given id does not exist it returns error response with 404 Not Found
	 * 
	 * @param	movieId - Id of movie
	 * @return  Movie for given id
	 */
	@GetMapping("/movies/{movieId}")
	public ResponseEntity<Movie> findMovieById(@PathVariable("movieId") Long movieId) {
		Movie movie = movieService.findMovieById(movieId);
		if (movie == null) {
			throw new NotFoundException("Movie not found: id=" + movieId);
		}
		return new ResponseEntity<>(movie, HttpStatus.OK);
	}
	
	/**
	 * Creates a movie in database. Returns created movie with 201 Created. 
	 * <p>
	 * If provided movie data is invalid it returns 400 Bad Request.
	 * 
	 * @param	movie - Movie to be created
	 * @return  Created movie 
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
	 * 
	 * @param	movieId - Id of movie
	 * @param	movie - Movie to be updated
	 * @return  Updated movie 
	 */	
	@PutMapping("/movies/{movieId}")
	public ResponseEntity<Movie> updateMovie(@RequestBody @Valid Movie movie, @PathVariable Long movieId) {
		boolean exists = movieService.isMovieExist(movieId);
		if (!exists) {
			throw new NotFoundException("Movie not found: id=" + movieId);
		}
		Movie newMovie = movieService.updateMovie(movieId, movie);
		return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
	}

	/**
	 * Deletes a movie with given id. Returns empty response with 204 No Content.
	 * <p>
	 * If movie with given id does not exist it returns error response with 404 Not Found
	 * 
	 * @param	movieId - Id of movie
	 * @return  Empty response 
	 */	
	@DeleteMapping("/movies/{movieId}")
	public ResponseEntity<Void> deleteMovie(@PathVariable Long movieId) {
		boolean exists = movieService.isMovieExist(movieId);
		if (!exists) {
			throw new NotFoundException("Movie not found: id=" + movieId);
		}
		movieService.deleteMovie(movieId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
//	@PostMapping("/movies/{movieId}/genres")
//	public ResponseEntity<Movie> addGenreToMovie(@PathVariable Long movieId, @RequestBody Genre genre) {
//		boolean movieExists = movieService.isMovieExist(movieId);
//		if (!movieExists) {
//			throw new NotFoundException("Movie not found: id=" + movieId);
//		}
//		Long genreId = genre.getId();
//		if (genreId == null) {
//			throw new IllegalArgumentException("Genre id required");
//		}
//		boolean genreExists = genreService.isGenreExist(genreId);
//		if (!genreExists) {
//			throw new IllegalArgumentException("Genre not found: id=" + genreId);
//		}
//		Movie movie = movieService.addGenreToMovie(movieId, genreId);
//		return new ResponseEntity<>(movie, HttpStatus.OK);
//	}
//	
//	@DeleteMapping("/movies/{movieId}/genres/{genreId}")
//	public ResponseEntity<Movie> addGenreToMovie(@PathVariable Long movieId, @PathVariable Long genreId) {
//		boolean movieExists = movieService.isMovieExist(movieId);
//		if (!movieExists) {
//			throw new NotFoundException("Movie not found: id=" + movieId);
//		}
//		boolean genreExists = genreService.isGenreExist(genreId);
//		if (!genreExists) {
//			throw new IllegalArgumentException("Genre not found: id=" + genreId);
//		}
//		Movie movie = movieService.removeGenreFromMovie(movieId, genreId);
//		return new ResponseEntity<>(movie, HttpStatus.OK);
//	}
}
