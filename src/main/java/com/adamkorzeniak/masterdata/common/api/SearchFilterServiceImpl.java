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
	public List<SearchFilter> buildFilters(Map<String, String> params, String propertyString) {
		List<SearchFilter> filters = new ArrayList<>();
		Iterator it = params.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			SearchFilter filter = new SearchFilter((String) pair.getKey(), (String) pair.getValue());
			validateAndFix(filter, propertyString);
			filters.add(filter);
		}
		return filters;
	}

	private void validateAndFix(SearchFilter filter, String propertyString) {
		String property = "params." + propertyString + "." + filter.getFunction().toString().toUpperCase();
		String allowedFieldsString = env.getProperty(property);
		if (allowedFieldsString == null) {
			throw new FilterNotSupportedException(filter.getFunction());
		}
		String[] allowedFields = allowedFieldsString.split(",");
		boolean isSupported = Arrays.asList(allowedFields).contains(filter.getField());
		if (!isSupported) {
			throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
		}
	}

}
