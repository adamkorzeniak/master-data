package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.shared.FilterParameter;
import com.adamkorzeniak.masterdata.shared.GenericSpecification;

@Service
public class MovieServiceImpl implements MovieService {
	
	private static final String GENRE_MATCH_KEY = "genre";
	
	@Autowired
	private MovieRepository movieRepository;

	@Override
	public List<Movie> searchMovies(Map<String,String> map) {
		String genreName = null;
		if (map.containsKey(GENRE_MATCH_KEY)) {
			genreName = map.get(GENRE_MATCH_KEY);
			map.remove(GENRE_MATCH_KEY);
		}
		List<FilterParameter> filters = MovieServiceHelper.buildFilters(map);
		Specification<Movie> spec = new GenericSpecification<>(filters);
		List<Movie> movies = movieRepository.findAll(spec);
		if (genreName == null) {
			return movies;
		}
		Genre genre = new Genre();
		genre.setName(genreName);
		return movies.stream()
					.filter(movie -> movie.getGenres().contains(genre))
					.collect(Collectors.toList());
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
