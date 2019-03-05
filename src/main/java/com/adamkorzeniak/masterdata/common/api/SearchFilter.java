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
		this.value = parseValue(value);
		this.key = key;
		processKey();
	}

	private String parseValue(String value) {
		if (key.split("-")[0].equals("order")) {
			return null;
		}
		return value;
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
