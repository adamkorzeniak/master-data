package com.adamkorzeniak.masterdata.features.movie.controller;

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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.exception.exceptions.PatchOperationNotSupportedException;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenrePatchDTO;
import com.adamkorzeniak.masterdata.features.movie.service.GenreService;
import com.adamkorzeniak.masterdata.features.movie.service.GenreServiceHelper;

@RestController
@RequestMapping("/v0/Movie")
public class GenreController {

    private static final String GENRE_RESOURCE_NAME = "Genre";
    private static final String MERGE_OPERATION = "merge";

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
    public ResponseEntity<List<GenreDTO>> findGenres(@RequestParam Map<String, String> allRequestParams) {

        List<GenreDTO> dtos = genreService.searchGenres(allRequestParams).stream().map(GenreServiceHelper::convertToDTO)
            .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    /**
     * Returns genre with given id with 200 OK.
     */
    @GetMapping("/genres/{genreId}")
    public ResponseEntity<GenreDTO> findGenreById(@PathVariable("genreId") Long genreId) {
        Optional<Genre> result = genreService.findGenreById(genreId);
        if (!result.isPresent()) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        GenreDTO dto = GenreServiceHelper.convertToDTO(result.get());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Creates a genre in database. Returns created genre with 201 Created.
     */
    @PostMapping("/genres")
    public ResponseEntity<GenreDTO> addGenre(@RequestBody @Valid GenreDTO dto) {
        Genre genre = GenreServiceHelper.convertToEntity(dto);
        Genre newGenre = genreService.addGenre(genre);
        return new ResponseEntity<>(GenreServiceHelper.convertToDTO(newGenre), HttpStatus.CREATED);
    }

    /**
     * Updates a genre with given id in database. Returns updated genre with 200 OK.
     */
    @PutMapping("/genres/{genreId}")
    public ResponseEntity<GenreDTO> updateGenre(@RequestBody @Valid GenreDTO dto, @PathVariable Long genreId) {
        boolean exists = genreService.isGenreExist(genreId);
        if (!exists) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        Genre genre = GenreServiceHelper.convertToEntity(dto);
        Genre newGenre = genreService.updateGenre(genreId, genre);
        return new ResponseEntity<>(GenreServiceHelper.convertToDTO(newGenre), HttpStatus.OK);
    }

    /**
     * Deletes a genre with given id. Returns empty response with 204 No Content.
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

    @PatchMapping("/genres/{genreId}")
    public ResponseEntity<GenreDTO> mergeGenres(@RequestBody GenrePatchDTO mergedto, @PathVariable Long genreId) {
        if (!MERGE_OPERATION.equals(mergedto.getOp())) {
            throw new PatchOperationNotSupportedException(mergedto.getOp(), GENRE_RESOURCE_NAME);
        }
        boolean oldExists = genreService.isGenreExist(genreId);
        boolean targetExists = genreService.isGenreExist(mergedto.getTargetGenreId());
        if (!oldExists) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, genreId);
        }
        if (!targetExists) {
            throw new NotFoundException(GENRE_RESOURCE_NAME, mergedto.getTargetGenreId());
        }
        Genre result = genreService.mergeGenres(genreId, mergedto.getTargetGenreId());
        genreService.deleteGenre(genreId);
        return new ResponseEntity<>(GenreServiceHelper.convertToDTO(result), HttpStatus.OK);
    }
}
