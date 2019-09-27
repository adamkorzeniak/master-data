package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.exception.exceptions.FieldFilterNotSupportedException;
import com.adamkorzeniak.masterdata.exception.exceptions.SearchFilterParamNotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class SearchFilterServiceImpl implements SearchFilterService {

	private final Environment env;
	
	@Autowired
    public SearchFilterServiceImpl(Environment env){
        this.env = env;
    }

	@Override
	public List<SearchFilterParam> buildFilters(Map<String, String> params, String propertyString) {
		List<SearchFilterParam> filters = new ArrayList<>();
		for (Entry<String, String> pair : params.entrySet()) {
			SearchFilterParam filter = new SearchFilterParam(pair.getKey(), pair.getValue());
			validate(filter, propertyString);
			filters.add(filter);
		}
		return filters;
	}

	private void validate(SearchFilterParam filter, String propertyString) {
		String property = "params." + propertyString + "." + filter.getFunction().getBaseType();
		String allowedFieldsString = env.getProperty(property);
		if (allowedFieldsString == null) {
			throw new SearchFilterParamNotSupportedException(filter.getFunction());
		}
		String[] allowedFields = allowedFieldsString.split(",");
		boolean isSupported = Arrays.asList(allowedFields).contains(filter.getField());
		if (!isSupported) {
			throw new FieldFilterNotSupportedException(filter.getFunction(), filter.getField());
		}
	}

}
