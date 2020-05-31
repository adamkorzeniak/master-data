package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.repository.MovieRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository,
                             ApiQueryService apiQueryService) {
        this.movieRepository = movieRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Movie> searchMovies(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Movie> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return movieRepository.findAll(spec);
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
