package com.adamkorzeniak.masterdata.common;

public enum FilterFunction {
	SEARCH, MATCH, MIN, MAX, EXIST, ORDER_ASC, ORDER_DESC;

	@Override
	public String toString() {
		String name = super.toString();
		return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
	}

}