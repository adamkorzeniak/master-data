package com.adamkorzeniak.masterdata.shared;

import java.io.Serializable;

import com.adamkorzeniak.masterdata.exception.InvalidQueryParamException;
import com.adamkorzeniak.masterdata.exception.InvalidQueryParamValueException;

import lombok.Getter;

public class FilterParameter implements Serializable {

	@Getter
	private FilterFunction function;
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
			processTextKey(elements, FilterFunction.SEARCH);
			break;
		case "match":
			processTextKey(elements, FilterFunction.MATCH);
			break;
		case "min":
			processNumericKey(elements, FilterFunction.MIN);
			break;
		case "max":
			processNumericKey(elements, FilterFunction.MAX);
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
			function = FilterFunction.ORDER_ASC;
		} else if (elements.length == 2) {
			switch (elements[1]) {
			case "asc":
				function = FilterFunction.ORDER_ASC;
				break;
			case "desc":
				function = FilterFunction.ORDER_DESC;
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

	private void processTextKey(String[] elements, FilterFunction functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		function = functionType;
		field = elements[1];
	}

	private void processNumericKey(String[] elements, FilterFunction functionType) {
		if (elements.length != 2) {
			throw new InvalidQueryParamException(key);
		}
		if (!isNumeric(this.value)) {
			throw new InvalidQueryParamValueException(functionType, key);
		}
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
}
