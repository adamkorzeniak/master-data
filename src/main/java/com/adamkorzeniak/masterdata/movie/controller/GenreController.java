package com.adamkorzeniak.masterdata.movie.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.service.GenreService;

@RestController
@RequestMapping("/Movie/api/v0")
public class GenreController {
	
	@Autowired
	private GenreService genreService;

	/**
	 * Returns list of all genres with 200 OK. 
	 * <p>
	 * If there are no genres it returns empty list with 204 No Content
	 * 
	 * @return  List of all genres
	 */
	@GetMapping("/genres")
	public ResponseEntity<List<Genre>> findGenres(
			@RequestParam(value="name", required=false) String name) {

		List<Genre> genres = (name != null && !name.isEmpty()) ? 
			genreService.findGenresByName(name): genreService.findAllGenres(); 
		
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}
	

	
	@GetMapping("/genres/search")
	public ResponseEntity<List<Genre>> findGenres(
			@RequestParam Map<String,String> allRequestParams
			) {
		
		List<Genre> genres = genreService.searchGenres(allRequestParams);
		if (genres.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

	/**
	 * Returns genre with given id with 200 OK. 
	 * <p>
	 * If genre with given id does not exist it returns error response with 404 Not Found
	 * 
	 * @param	genreId - Id of movie
	 * @return  Genre for given id
	 */
	@GetMapping("/genres/{genreId}")
	public ResponseEntity<Genre> findGenreById(@PathVariable("genreId") Long genreId) {
		Optional<Genre> result = genreService.findGenreById(genreId);
		if (!result.isPresent()) {
			throw new NotFoundException("Genre not found: id=" + genreId);
		}
		return new ResponseEntity<>(result.get(), HttpStatus.OK);
	}

	/**
	 * Creates a genre in database. Returns created genre with 201 Created. 
	 * <p>
	 * If provided genre data is invalid it returns 400 Bad Request.
	 * 
	 * @param	genre - Movie to be created
	 * @return  Created genre 
	 */
	@PostMapping("/genres")
	public ResponseEntity<Genre> addGenre(@RequestBody @Valid Genre genre) {
		Genre newGenre = genreService.addGenre(genre);
		return new ResponseEntity<>(newGenre, HttpStatus.CREATED);
	}
	
	/**
	 * Updates a genre with given id in database. Returns updated genre with 200 OK.
	 * <p>
	 * If genre with given id does not exist it returns error response with 404 Not Found
	 * <p>
	 * If provided genre data is invalid it returns 400 Bad Request.
	 * 
	 * @param	genreId - Id of genre
	 * @param	genre - Genre to be updated
	 * @return  Updated genre 
	 */	
	@PutMapping("/genres/{genreId}")
	public ResponseEntity<Genre> updateGenre(@RequestBody @Valid Genre genre, @PathVariable Long genreId) {
		boolean exists = genreService.isGenreExist(genreId);
		if (!exists) {
			throw new NotFoundException("Genre not found: id=" + genreId);
		}
		Genre newGenre = genreService.updateGenre(genreId, genre);
		return new ResponseEntity<>(newGenre, HttpStatus.CREATED);
	}

	/**
	 * Deletes a genre with given id. Returns empty response with 204 No Content.
	 * <p>
	 * If genre with given id does not exist it returns error response with 404 Not Found
	 * 
	 * @param	genreId - Id of movie
	 * @return  Empty response 
	 */	
	@DeleteMapping("/genres/{genreId}")
	public ResponseEntity<Void> deleteGenre(@PathVariable Long genreId) {
		boolean exists = genreService.isGenreExist(genreId);
		if (!exists) {
			throw new NotFoundException("Genre not found: id=" + genreId);
		}
		genreService.deleteGenre(genreId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
