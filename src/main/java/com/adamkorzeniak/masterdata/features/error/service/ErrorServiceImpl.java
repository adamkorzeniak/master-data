package com.adamkorzeniak.masterdata.features.error.service;

import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.error.model.Error;
import com.adamkorzeniak.masterdata.features.error.repository.ErrorRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ErrorServiceImpl implements ErrorService {

    private final ErrorRepository errorRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ErrorServiceImpl(ErrorRepository errorRepository,
                            ApiQueryService apiQueryService) {
        this.errorRepository = errorRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Error> searchErrors(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Error> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return errorRepository.findAll(spec);
    }

    @Override
    public Optional<Error> findErrorById(Long id) {
        return errorRepository.findById(id);
    }

    @Override
    public Error addError(Error error) {
        error.setId(-1L);
        return errorRepository.save(error);
    }

    @Override
    public Error updateError(Long id, Error error) {
        error.setId(id);
        return errorRepository.save(error);
    }

    @Override
    public void deleteError(Long id) {
        errorRepository.deleteById(id);
    }

    @Override
    public boolean isErrorExist(Long id) {
        return errorRepository.existsById(id);
    }
}
