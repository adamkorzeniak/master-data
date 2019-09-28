package com.adamkorzeniak.masterdata.api;

import java.util.List;
import java.util.Map;

public interface SearchFilterService {

    /**
     * Builds list of API Search Query Params object representation
     */
    List<SearchFilterParam> buildFilters(Map<String, String> map, String propertyLocation);
}