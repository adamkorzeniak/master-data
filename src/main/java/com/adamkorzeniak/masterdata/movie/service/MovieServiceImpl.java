package com.adamkorzeniak.masterdata.movie.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.common.FilterParameter;
import com.adamkorzeniak.masterdata.common.GenericSpecification;
import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

	private static final String GENRE_MATCH_KEY = "genres";

	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> searchMovies(Map<String, String> map) {
		String genreString = null;
		if (map.containsKey(GENRE_MATCH_KEY)) {
			genreString = map.get(GENRE_MATCH_KEY);
			map.remove(GENRE_MATCH_KEY);
		}
		List<FilterParameter> filters = MovieServiceHelper.buildFilters(map);
		Specification<Movie> spec = new GenericSpecification<>(filters);
		List<Movie> movies = movieRepository.findAll(spec);
		if (genreString == null) {
			return movies;
		}
		String[] genreArray = genreString.split(",");
		return movies.stream()
			.filter(movie -> containsSearchedGenres(movie, genreArray))
			.collect(Collectors.toList());
	}
	
	private boolean containsSearchedGenres(Movie movie, String[] searchedGenres) {
		List<Genre> genres = movie.getGenres();
		for (String s: searchedGenres) {
			boolean found = genres.stream().anyMatch(
					genre -> genre.getName().toLowerCase().contains(s.toLowerCase()));
			if (!found) {
				return false;
			}
		}
		return true;
		
	}

	@Override
	public Optional<Movie> findMovieById(Long id) {
		return movieRepository.findById(id);
	}

	@Override
	public Movie addMovie(Movie movie) {
		movie.setId(-1L);
		return movieRepository.save(movie);
	}

	@Override
	public Movie updateMovie(Long id, Movie movie) {
		movie.setId(id);
		return movieRepository.save(movie);
	}

	@Override
	public void deleteMovie(Long id) {
		movieRepository.deleteById(id);
	}

	@Override
	public boolean isMovieExist(Long id) {
		return movieRepository.existsById(id);
	}
}
