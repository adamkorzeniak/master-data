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

	@Autowired
	private FilmwebService filmwebService;

	@GetMapping("/movies/filmweb/popular")
	public ResponseEntity<List<MovieDTO>> retrieveMoviesFromFilmweb(@RequestParam int count) {

		List<String> movieURLs = filmwebService.getPopularMoviesUlrs(count);
		List<MovieDTO> movieDTOs = movieURLs.stream().map(url -> filmwebService.getMovieDetails(url))
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
