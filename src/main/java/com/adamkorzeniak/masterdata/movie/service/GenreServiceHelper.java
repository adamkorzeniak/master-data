package com.adamkorzeniak.masterdata.movie.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.adamkorzeniak.masterdata.shared.FilterParameter;

public class GenreServiceHelper {

	public static List<FilterParameter> buildFilters(Map<String, String> map) {

		List<FilterParameter> filters = new ArrayList<>();

		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			FilterParameter filter = new FilterParameter((String) pair.getKey(), (String) pair.getValue());
			validateAndFix(filter);
			filters.add(filter);
		}

		return filters;
	}

	private static void validateAndFix(FilterParameter filter) {
		switch (filter.getFunction()) {
			case MIN:
			case MAX:
				throw new RuntimeException(filter.getFunction() + " is doesn't supported");
			case SEARCH:
				if (!Arrays.asList("name").contains(filter.getField())) {
					throw new RuntimeException("Search doesn't support " + filter.getField());
				}
				break;
			case MATCH:
				if (!Arrays.asList("name").contains(filter.getField())) {
					throw new RuntimeException("Match doesn't support " + filter.getField());
				}
				break;
			case ORDER_ASC:
			case ORDER_DESC:
				if (!Arrays.asList("name").contains(filter.getField())) {
					throw new RuntimeException(filter.getFunction() + " doesn't support " + filter.getField());
				}
				break;
		}

	}
}
