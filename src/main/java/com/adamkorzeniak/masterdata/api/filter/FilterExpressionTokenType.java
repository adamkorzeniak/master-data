package com.adamkorzeniak.masterdata.api.filter;

public enum FilterExpressionTokenType {
    EQUALS,
    NOT_EQUALS,
    LESS,
    GREATER,
    LESS_OR_EQUAL,
    GREATER_OR_EQUAL,
    LIKE,
    IS_NULL,
    IS_NOT_NULL;

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}