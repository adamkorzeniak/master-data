package com.adamkorzeniak.masterdata.error.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.error.repository.ErrorRepository;
import com.adamkorzeniak.masterdata.common.GenericSpecification;
import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFilterService;
import com.adamkorzeniak.masterdata.error.model.Error;

@Service
public class ErrorServiceImpl implements ErrorService {
	
	@Autowired
	private ErrorRepository errorRepository;
	
	@Autowired
	private SearchFilterService searchFilterService;

	@Override
	public List<Error> searchErrors(Map<String, String> requestParams) {
		List<SearchFilter> filters = searchFilterService.buildFilters(requestParams, "movie.genres");
		Specification<Error> spec = new GenericSpecification<>(filters);
		return errorRepository.findAll(spec);	
	}

	@Override
	public Error addError(com.adamkorzeniak.masterdata.error.model.Error error) {
		error.setId(-1L);
		return errorRepository.save(error);
	}

	@Override
	public void deleteError(Long id) {
		errorRepository.deleteById(id);
	}

	@Override
	public boolean isErrorExist(Long errorId) {
		return errorRepository.existsById(errorId);
	}

}
