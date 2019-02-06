package com.adamkorzeniak.masterdata.shared;

import java.io.Serializable;

import lombok.Getter;

public class FilterParameter implements Serializable {
	
	private static final String INVALID_QUERY_PARAM_MESSAGE = "Invalid query param: ";
	@Getter
	private Function function;
	@Getter
	private String value;
	@Getter
	private String field;
	
	private String key;

	public FilterParameter(String key, String value) {
		this.value = value;
		this.key = key;
		processKey();
	}

	private void processKey() {
		String[] elements = key.split("-");
		switch (elements[0]) {
		case "search":
			processTextKey(elements, Function.SEARCH);
			break;
		case "match":
			processTextKey(elements, Function.MATCH);
			break;
		case "min":
			processNumericKey(elements, Function.MIN);
			break;
		case "max":
			processNumericKey(elements, Function.MAX);
			break;
		case "order":
			processOrderKey(elements);
			break;
		default:
			throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key);
		}
	}

	private void processOrderKey(String[] elements) {
		if (elements.length == 1) {
			function = Function.ORDER_ASC;
		} else if (elements.length == 2) {
			switch (elements[1]) {
				case "asc":
					function = Function.ORDER_ASC;
					break;
				case "desc":
					function = Function.ORDER_DESC;
					break;
				default:
					throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key);
			}
			
		} else {
			throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key);
		}
		field = value;
		value = null;
	}

	private void processTextKey(String[] elements, Function functionType) {
		if (elements.length != 2)
			throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key);
		if (!isNumeric(this.value))
			throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key + ". " + functionType + " supports only numbers");
		function = functionType;
		field = elements[1];
	}
	
	private void processNumericKey(String[] elements, Function functionType) {
		if (elements.length != 2)
			throw new RuntimeException(INVALID_QUERY_PARAM_MESSAGE + key);
		function = functionType;
		field = elements[1];
	}

	private boolean isNumeric(String value) {
		try {
			Double.parseDouble(value);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public enum Function {
		SEARCH, MATCH, MIN, MAX, ORDER_ASC, ORDER_DESC
	}

}
