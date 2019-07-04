package com.adamkorzeniak.masterdata.api;

import java.io.Serializable;

import com.adamkorzeniak.masterdata.exception.exceptions.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.exceptions.InvalidQueryParamValueException;

import lombok.Getter;

/**
 * 
 * API Search Query Param object holding representation of search query
 * parameter
 *
 */
public class SearchFilterParam implements Serializable {

	private static final long serialVersionUID = -3844292648390012518L;

	@Getter
	private SearchFunctionType function;
	@Getter
	private String value;
	@Getter
	private String field;

	private final String key;

	/**
	 * 
	 * Builds API Search Query Param representation object
	 *
	 * @param key - query param (i.e. search-title)
	 * @param value - query param value (i.e. Titanic)
	 */
	public SearchFilterParam(String key, String value) {
		this.key = key;
		this.value = value;
		processKey();
	}

	private void processKey() {
		String[] elements = key.split("-");
		switch (elements[0]) {
		case "search":
			processTextKey(elements, SearchFunctionType.SEARCH);
			break;
		case "match":
			processTextKey(elements, SearchFunctionType.MATCH);
			break;
		case "min":
			processNumericKey(elements, SearchFunctionType.MIN);
			break;
		case "max":
			processNumericKey(elements, SearchFunctionType.MAX);
			break;
		case "exist":
			processBooleanKey(elements, SearchFunctionType.EXIST);
			break;
		case "order":
			processOrderKey(elements);
			break;
		default:
			throw new InvalidQueryParamException(key);
		}
	}

	private void processOrderKey(String[] elements) {
		if (elements.length > 2) {
			throw new InvalidQueryParamException(key);
		}
		if (elements.length == 1) {
			function = SearchFunctionType.ORDER_ASC;
		} else if (elements.length == 2) {
			function = parseOrderType(elements[1]);
		}
		field = value;
		value = null;
	}

	private SearchFunctionType parseOrderType(String orderType) {
		switch (orderType) {
		case "asc":
			return SearchFunctionType.ORDER_ASC;
		case "desc":
			return SearchFunctionType.ORDER_DESC;
		default:
			throw new InvalidQueryParamException(key);
		}
	}

	private void processTextKey(String[] elements, SearchFunctionType functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		function = functionType;
		field = elements[1];
	}

	private void processNumericKey(String[] elements, SearchFunctionType functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		if (!isNumeric(value)) {
			throw new InvalidQueryParamValueException(functionType, key);
		}
		function = functionType;
		field = elements[1];
	}

	private void processBooleanKey(String[] elements, SearchFunctionType functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		if (!isBoolean(value)) {
			throw new InvalidQueryParamValueException(functionType, key);
		}
		function = functionType;
		field = elements[1];
	}

	private boolean isBoolean(String value) {
		return value.equals("true") || value.equals("false");
	}

	private boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
