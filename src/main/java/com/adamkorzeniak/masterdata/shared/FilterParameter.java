package com.adamkorzeniak.masterdata.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
public class FilterParameter {

	private Function function;
	private String value;
	@Setter
	private String field;

	public FilterParameter(String key, String value) {
		this.value = value;
		extractFromKey(key);
	}

	private void extractFromKey(String key) {
		String[] elements = key.split("-");
		switch (elements[0]) {
		case "search":
			if (elements.length != 2)
				throw new RuntimeException("Invalid query param: " + key);
			function = Function.SEARCH;
			field = elements[1];
			break;
		case "match":
			if (elements.length != 2)
				throw new RuntimeException("Invalid query param: " + key);
			function = Function.MATCH;
			field = elements[1];
			break;
		case "min":
			if (elements.length != 2)
				throw new RuntimeException("Invalid query param: " + key);
			if (!isNumeric(this.value))
				throw new RuntimeException("Invalid query param: " + key + ". Min supports only numbers");
			function = Function.MIN;
			field = elements[1];
			break;
		case "max":
			if (elements.length != 2)
				throw new RuntimeException("Invalid query param: " + key);
			if (!isNumeric(this.value))
				throw new RuntimeException("Invalid query param: " + key + ". Min supports only numbers");
			function = Function.MAX;
			field = elements[1];
			break;
		case "order":
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
						throw new RuntimeException("Invalid query param: " + key);
				}
				
			} else {
				throw new RuntimeException("Invalid query param: " + key);
			}
			field = value;
			value = null;
			break;
		default:
			throw new RuntimeException("Invalid query param: " + key);
		}
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
