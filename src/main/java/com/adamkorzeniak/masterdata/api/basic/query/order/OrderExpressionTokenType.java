package com.adamkorzeniak.masterdata.api.basic.query.order;

public enum OrderExpressionTokenType {
    ASCENDING,
    DESCENDING;

    @Override
    public String toString() {
        String name = super.toString();
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }
}