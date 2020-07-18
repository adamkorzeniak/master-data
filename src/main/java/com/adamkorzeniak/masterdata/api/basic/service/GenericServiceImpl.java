package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.entity.DatabaseEntity;
import com.adamkorzeniak.masterdata.api.common.providers.RepositoryProviderService;
import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpression;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GenericServiceImpl implements GenericService {

    private final RepositoryProviderService repositoryProviderService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public GenericServiceImpl(RepositoryProviderService repositoryProviderService,
                              ApiQueryService apiQueryService) {
        this.repositoryProviderService = repositoryProviderService;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Object> searchAll(Map<String, String> queryParams, Class<? extends DatabaseEntity> resourceClass) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        GenericSpecification<Object> spec = new GenericSpecification<>(filterExpression, orderExpression);
        JpaSpecificationExecutor specificationExecutor = repositoryProviderService.getExecutor(resourceClass);
        return specificationExecutor.findAll(spec);
    }

    @Override
    public Optional<Object> findById(Long id, Class<? extends DatabaseEntity> resourceClass) {
        JpaRepository jpaRepository = repositoryProviderService.getRepository(resourceClass);
        return jpaRepository.findById(id);
    }

    @Override
    public Object add(DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass) {
        JpaRepository jpaRepository = repositoryProviderService.getRepository(resourceClass);
        entity.setId(-1L);
        return jpaRepository.save(entity);
    }

    @Override
    public boolean exists(Long id, Class<? extends DatabaseEntity> resourceClass) {
        JpaRepository jpaRepository = repositoryProviderService.getRepository(resourceClass);
        return jpaRepository.existsById(id);
    }

    @Override
    public Object update(Long id, DatabaseEntity entity, Class<? extends DatabaseEntity> resourceClass) {
        JpaRepository jpaRepository = repositoryProviderService.getRepository(resourceClass);
        entity.setId(id);
        return jpaRepository.save(entity);
    }

    @Override
    public void delete(Long id, Class<? extends DatabaseEntity> resourceClass) {
        JpaRepository jpaRepository = repositoryProviderService.getRepository(resourceClass);
        jpaRepository.deleteById(id);
    }
}
