package com.adamkorzeniak.masterdata.api;

/**
 * Represents API Search Param Function Type
 */
public enum SearchFunctionType {
    SEARCH, MATCH, MIN, MAX, EXIST, ORDER_ASC, ORDER_DESC;

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getBaseType() {
        if (this == ORDER_DESC || this == ORDER_ASC) {
            return "ORDER";
        }
        return toString().toUpperCase();
    }
}