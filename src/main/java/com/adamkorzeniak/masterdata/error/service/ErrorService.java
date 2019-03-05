package com.adamkorzeniak.masterdata.error.service;

import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.movie.model.Genre;

public interface ErrorService {

	List<Genre> searchErrors(Map<String, String> requestParams);

	Error addGenre(Error error);

	void deleteError(Long id);
}
