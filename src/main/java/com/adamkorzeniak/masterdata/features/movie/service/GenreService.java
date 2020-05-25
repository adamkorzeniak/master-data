package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface GenreService {

    List<Genre> searchGenres(Map<String, String> allRequestParams);

    Optional<Genre> findGenreById(Long id);

    Genre addGenre(Genre genre);

    Genre updateGenre(Long id, Genre genre);

    void deleteGenre(Long id);

    boolean isGenreExist(Long id);
}
