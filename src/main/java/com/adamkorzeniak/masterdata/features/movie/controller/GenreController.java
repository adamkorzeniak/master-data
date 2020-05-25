package com.adamkorzeniak.masterdata.features.movie.controller;

import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.service.GenreService;
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
public class GenreController {

    private static final String GENRE_RESOURCE_NAME = "Genre";

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Returns list of genres with 200 OK.
     * <p>
     * If there are no genres it returns empty list with 204 No Content
     */
    @GetMapping("/genres")
    public ResponseEntity<List<Genre>> findGenres(@RequestParam Map<String, String> allRequestParams) {
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
     */
    @GetMapping("/genres/{genreId}")
    public ResponseEntity<Genre> findGenreById(@PathVariable("genreId") Long genreId) {
        Optional<Genre> genre = genreService.findGenreById(genreId);
        if (genre.isEmpty()) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        return new ResponseEntity<>(genre.get(), HttpStatus.OK);
    }

    /**
     * Creates a genre in database.
     * Returns created genre with 201 Created.
     * <p>
     * If provided genre data is invalid it returns 400 Bad Request.
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
     */
    @PutMapping("/genres/{genreId}")
    public ResponseEntity<Genre> updateGenre(@RequestBody @Valid Genre genre, @PathVariable Long genreId) {
        boolean exists = genreService.isGenreExist(genreId);
        if (!exists) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        Genre newGenre = genreService.updateGenre(genreId, genre);
        return new ResponseEntity<>(newGenre, HttpStatus.OK);
    }

    /**
     * Deletes a genre with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If genre with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/genres/{genreId}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long genreId) {
        boolean exists = genreService.isGenreExist(genreId);
        if (!exists) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        genreService.deleteGenre(genreId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
