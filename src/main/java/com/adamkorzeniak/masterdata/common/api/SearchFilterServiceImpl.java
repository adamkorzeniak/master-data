package com.adamkorzeniak.masterdata.common.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.exception.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.FilterNotSupportedException;

@Service
public class SearchFilterServiceImpl implements SearchFilterService {
	
	@Autowired
	public Environment env;
	
	@Override
	public List<SearchFilter> buildFilters(Map<String, String> map, String propertyPrefix) {
		List<SearchFilter> filters = new ArrayList<>();
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			SearchFilter filter = new SearchFilter((String) pair.getKey(), (String) pair.getValue());
			validateAndFix(filter, propertyPrefix);
			filters.add(filter);
		}
		return filters;
	}

	private void validateAndFix(SearchFilter filter, String propertyPrefix) {
		String propertyLocation = "params." + propertyPrefix + "." + filter.getFunction().toString().toUpperCase();
		String allowedString = env.getProperty(propertyLocation);
		if (allowedString == null) {
			throw new FilterNotSupportedException(filter.getFunction());
		}
		String[] allowedFields = allowedString.split(",");
		boolean isSupported = Arrays.asList(allowedFields).contains(filter.getField());
		if (!isSupported) {
			throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
		}
	}

}
