package com.adamkorzeniak.masterdata.search.service;


import com.adamkorzeniak.masterdata.search.model.SearchResult;

import java.util.List;

public interface SearchService {

    List<SearchResult> searchAll(String searchQuery);
}
