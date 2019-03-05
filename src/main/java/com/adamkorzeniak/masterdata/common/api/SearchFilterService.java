package com.adamkorzeniak.masterdata.common.api;

import java.util.List;
import java.util.Map;

public interface SearchFilterService {

	List<SearchFilter> buildFilters(Map<String, String> map, String propertyLocation);

}