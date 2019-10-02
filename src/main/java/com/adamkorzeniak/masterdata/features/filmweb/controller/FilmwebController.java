package com.adamkorzeniak.masterdata.features.filmweb.controller;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.features.filmweb.service.FilmwebService;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;
import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;

@RestController
@RequestMapping("/v0/Movie")
public class FilmwebController {

    private final FilmwebService filmwebService;

    @Autowired
    public FilmwebController(FilmwebService filmwebService) {
        this.filmwebService = filmwebService;
    }

    @GetMapping("/movies/filmweb/popular")
    public ResponseEntity<List<MovieDTO>> retrieveMoviesFromFilmweb(@RequestParam int count) {

        List<String> movieURLs = filmwebService.getPopularMoviesUrls(count);
        List<MovieDTO> movieDTOs = movieURLs.stream().map(filmwebService::getMovieDetails)
            .collect(Collectors.toList());

        SortedSet<String> genres = new TreeSet<>();
        for (MovieDTO movie : movieDTOs) {
            List<GenreDTO> dtos = movie.getGenres();
            for (GenreDTO dto : dtos) {
                genres.add(dto.getName());
            }
        }
        return new ResponseEntity<>(movieDTOs, HttpStatus.OK);
    }
}
