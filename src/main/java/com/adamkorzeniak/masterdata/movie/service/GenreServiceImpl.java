package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.common.GenericSpecification;
import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFilterService;
import com.adamkorzeniak.masterdata.exception.NotFoundException;
import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.model.Movie;
import com.adamkorzeniak.masterdata.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.movie.repository.MovieRepository;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private SearchFilterService searchFilterService;

	@Override
	public List<Genre> searchGenres(Map<String, String> requestParams) {
		List<SearchFilter> filters = searchFilterService.buildFilters(requestParams, "movie.genres");
		Specification<Genre> spec = new GenericSpecification<>(filters);
		return genreRepository.findAll(spec);
	}

	@Override
	public Optional<Genre> findGenreById(Long id) {
		return genreRepository.findById(id);
	}

	@Override
	public Genre addGenre(Genre genre) {
		genre.setId(-1L);
		return genreRepository.save(genre);
	}

	@Override
	public Genre updateGenre(Long id, Genre genre) {
		genre.setId(id);
		return genreRepository.save(genre);
	}

	@Override
	public void deleteGenre(Long id) {
		genreRepository.deleteById(id);
	}

	@Override
	public boolean isGenreExist(Long id) {
		return genreRepository.existsById(id);
	}

	@Override
	public Genre mergeGenres(Long oldGenreId, Long targetGenreId) {
		Optional<Genre> oldResult = genreRepository.findById(oldGenreId);
		Optional<Genre> targetResult = genreRepository.findById(targetGenreId);
		if (!oldResult.isPresent()) {
			throw new NotFoundException("Genre", oldGenreId);
		}
		if (!targetResult.isPresent()) {
			throw new NotFoundException("Genre", targetGenreId);
		}
		Genre oldGenre = oldResult.get();
		Genre targetGenre = targetResult.get();
		List<Movie> movies = movieRepository.findByGenresContaining(oldGenre);
		movies.stream()
			.forEach(movie -> {
			List<Genre> genres = movie.getGenres();
			int index = genres.indexOf(oldGenre);
			if (genres.contains(targetGenre)) {
				genres.remove(index);
			} else {
				genres.set(index, targetGenre);
			}
		});
		return targetGenre;
	}

}
