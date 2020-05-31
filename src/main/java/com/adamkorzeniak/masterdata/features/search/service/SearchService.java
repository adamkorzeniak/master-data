package com.adamkorzeniak.masterdata.features.search.service;


import com.adamkorzeniak.masterdata.features.search.model.SearchResult;

import java.util.List;

public interface SearchService {

    List<SearchResult> searchAll(String searchQuery);
}
