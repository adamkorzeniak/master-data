package com.adamkorzeniak.masterdata.api.search.service;

import com.adamkorzeniak.masterdata.api.basic.DatabaseEntity;
import com.adamkorzeniak.masterdata.api.common.providers.RepositoryProviderService;
import com.adamkorzeniak.masterdata.persistence.SearchSpecification;
import com.adamkorzeniak.masterdata.api.search.model.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    private final RepositoryProviderService repositoryProviderService;

    @Autowired
    public SearchServiceImpl(RepositoryProviderService repositoryProviderService) {
        this.repositoryProviderService = repositoryProviderService;
    }


    @Override
    public List<SearchResult> searchAll(String searchQuery) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (Map.Entry<Class<? extends DatabaseEntity>, JpaSpecificationExecutor<? extends DatabaseEntity>> entry : repositoryProviderService.getAllRepositories().entrySet()) {
            List<DatabaseEntity> results = entry.getValue().findAll(new SearchSpecification(searchQuery, entry.getKey()));
            searchResults.addAll(buildResults(entry.getKey().getName(), results));
        }
        return searchResults;
    }

    private List<SearchResult> buildResults(String entityType, List<?> entityResults) {
        List<SearchResult> searchResults = new ArrayList<>();
        for (java.lang.Object entity : entityResults) {
            searchResults.add(new SearchResult(entityType, entity));
        }
        return searchResults;
    }
}
