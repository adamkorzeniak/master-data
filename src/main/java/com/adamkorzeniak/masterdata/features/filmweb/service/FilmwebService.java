package com.adamkorzeniak.masterdata.features.filmweb.service;

import java.util.List;

import com.adamkorzeniak.masterdata.features.movie.model.dto.MovieDTO;

public interface FilmwebService {

    MovieDTO getMovieDetails(String movieUrl);

    List<String> getPopularMoviesUrls(int count);

}