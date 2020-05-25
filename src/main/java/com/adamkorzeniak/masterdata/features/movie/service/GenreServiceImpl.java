package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository,
                            ApiQueryService apiQueryService) {
        this.genreRepository = genreRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Genre> searchGenres(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Genre> spec = new GenericSpecification<>(filterExpression, orderExpression);
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
