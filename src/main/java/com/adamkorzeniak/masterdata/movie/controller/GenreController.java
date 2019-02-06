package com.adamkorzeniak.masterdata.movie.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.adamkorzeniak.masterdata.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.movie.service.GenreService;
import com.adamkorzeniak.masterdata.movie.service.GenreServiceHelper;

@RestController
@RequestMapping("/Movie/api/v0")
public class GenreController {
	
	@Autowired
	private GenreService genreService;

	/**
	 * Returns list of genres with 200 OK. 
	 * <p>
	 * If there are no genres it returns empty list with 204 No Content
	 * 
	 * @return  List of genres
	 */
	
	@GetMapping("/genres")
	public ResponseEntity<List<GenreDTO>> findGenres(
			@RequestParam Map<String,String> allRequestParams
			) {
		
		List<GenreDTO> dtos = genreService.searchGenres(allRequestParams).stream()
				.map(genre -> GenreServiceHelper.convertToDTO(genre))
				.collect(Collectors.toList());
				
		if (dtos.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(dtos, HttpStatus.OK);
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
	public ResponseEntity<GenreDTO> findGenreById(@PathVariable("genreId") Long genreId) {
		Optional<Genre> result = genreService.findGenreById(genreId);
		if (!result.isPresent()) {
			throw new NotFoundException("Genre not found: id=" + genreId);
		}
		GenreDTO dto = GenreServiceHelper.convertToDTO(result.get());
		return new ResponseEntity<>(dto, HttpStatus.OK);
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
	public ResponseEntity<GenreDTO> addGenre(@RequestBody @Valid GenreDTO dto) {
		Genre genre = GenreServiceHelper.convertToEntity(dto);
		Genre newGenre = genreService.addGenre(genre);
		return new ResponseEntity<>(GenreServiceHelper.convertToDTO(newGenre), HttpStatus.CREATED);
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
	public ResponseEntity<GenreDTO> updateGenre(@RequestBody @Valid GenreDTO dto, @PathVariable Long genreId) {
		boolean exists = genreService.isGenreExist(genreId);
		if (!exists) {
			throw new NotFoundException("Genre not found: id=" + genreId);
		}
		Genre genre = GenreServiceHelper.convertToEntity(dto);
		Genre newGenre = genreService.updateGenre(genreId, genre);
		return new ResponseEntity<>(GenreServiceHelper.convertToDTO(newGenre), HttpStatus.CREATED);
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
