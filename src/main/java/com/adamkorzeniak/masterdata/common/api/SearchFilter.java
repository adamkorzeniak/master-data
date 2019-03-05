package com.adamkorzeniak.masterdata.common.api;

import java.io.Serializable;

import com.adamkorzeniak.masterdata.exception.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamValueException;

import lombok.Getter;

public class SearchFilter implements Serializable {

	@Getter
	private SearchFunctionType function;
	@Getter
	private String value;
	@Getter
	private String field;

	private String key;

	public SearchFilter(String key, String value) {
		this.value = value;
		this.key = key;
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
		if (elements.length == 1) {
			function = SearchFunctionType.ORDER_ASC;
		} else if (elements.length == 2) {
			switch (elements[1]) {
			case "asc":
				function = SearchFunctionType.ORDER_ASC;
				break;
			case "desc":
				function = SearchFunctionType.ORDER_DESC;
				break;
			default:
				throw new InvalidQueryParamException(key);
			}

		} else {
			throw new InvalidQueryParamException(key);
		}
		field = value;
		value = null;
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
		if (!isNumeric(this.value)) {
			throw new InvalidQueryParamValueException(functionType, key);
		}
		function = functionType;
		field = elements[1];
	}

	private void processBooleanKey(String[] elements, SearchFunctionType functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		if (!isBoolean(this.value)) {
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
