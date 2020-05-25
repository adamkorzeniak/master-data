package com.adamkorzeniak.masterdata.api.filter;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class FilterExpression {

    @Getter
    private List<FilterExpressionToken> filters;

    public FilterExpression() {
        this.filters = new ArrayList<>();
    }

}
