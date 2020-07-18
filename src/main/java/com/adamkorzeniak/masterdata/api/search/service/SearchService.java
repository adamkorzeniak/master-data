package com.adamkorzeniak.masterdata.api.search.service;


import com.adamkorzeniak.masterdata.api.search.model.SearchResult;

import java.util.List;

public interface SearchService {

    List<SearchResult> searchAll(String searchQuery);
}
