package com.adamkorzeniak.masterdata.movie.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.movie.model.Genre;
import com.adamkorzeniak.masterdata.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.shared.FilterParameter;
import com.adamkorzeniak.masterdata.shared.GenericSpecification;

@Service
public class GenreServiceImpl implements GenreService {

	@Autowired
	private GenreRepository genreRepository;

	@Override
	public List<Genre> searchGenres(Map<String, String> map) {
		List<FilterParameter> filters = GenreServiceHelper.buildFilters(map);
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

}
