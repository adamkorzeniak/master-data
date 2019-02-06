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
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private GenreService genreService;

	/**
	 * @inheritDoc
	 */
	@Override
	public List<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	@Override
	public Movie findMovieById(Long id) {
		Optional<Movie> movie = movieRepository.findById(id);
		return movie.isPresent() ? movie.get() : null;
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

	@Override
	public List<Movie> searchMovies(Map<String,String> map) {
		String genreName = null;
		if (map.containsKey("genre")) {
			genreName = map.get("genre");
			map.remove("genre");
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

//	@Override
//	public Movie addGenreToMovie(Long movieId, Long genreId) {
//		Optional<Movie> opMovie = movieRepository.findById(movieId);
//		Genre genre = genreService.findGenreById(genreId);
//		if (!opMovie.isPresent() || genre == null) {
//			throw new RuntimeException("Very unexpected exception");
//		}
//		Movie movie = opMovie.get();
//		movie.addGenre(genre);
//		return movieRepository.save(movie);
//	}
//
//	@Override
//	public Movie removeGenreFromMovie(Long movieId, Long genreId) {
//		Optional<Movie> opMovie = movieRepository.findById(movieId);
//		Genre genre = genreService.findGenreById(genreId);
//		if (!opMovie.isPresent() || genre == null) {
//			throw new RuntimeException("Very unexpected exception");
//		}
//		Movie movie = opMovie.get();
//		movie.removeGenre(genre);
//		return movieRepository.save(movie);
//	}

}
