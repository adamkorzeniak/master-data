package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.api.GenericSpecification;
import com.adamkorzeniak.masterdata.api.SearchFilterParam;
import com.adamkorzeniak.masterdata.api.SearchFilterService;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.Movie;
import com.adamkorzeniak.masterdata.features.movie.repository.GenreRepository;
import com.adamkorzeniak.masterdata.features.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;
    private final SearchFilterService searchFilterService;

    @Autowired
    public GenreServiceImpl(GenreRepository genreRepository,
                            MovieRepository movieRepository,
                            SearchFilterService searchFilterService) {
        this.genreRepository = genreRepository;
        this.movieRepository = movieRepository;
        this.searchFilterService = searchFilterService;
    }

    @Override
    public List<Genre> searchGenres(Map<String, String> requestParams) {
        List<SearchFilterParam> filters = searchFilterService.buildFilters(requestParams, "movie.genres");
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
        if (oldResult.isEmpty()) {
            throw new NotFoundException("Genre", oldGenreId);
        }
        if (targetResult.isEmpty()) {
            throw new NotFoundException("Genre", targetGenreId);
        }
        Genre oldGenre = oldResult.get();
        Genre targetGenre = targetResult.get();
        List<Movie> movies = movieRepository.findByGenresContaining(oldGenre);
        for (Movie movie : movies) {
            List<Genre> genres = movie.getGenres();
            int index = genres.indexOf(oldGenre);
            if (genres.contains(targetGenre)) {
                genres.remove(index);
            } else {
                genres.set(index, targetGenre);
            }
        }
        return targetGenre;
    }

}
