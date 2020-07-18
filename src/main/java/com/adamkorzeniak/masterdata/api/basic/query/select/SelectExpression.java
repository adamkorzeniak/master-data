package com.adamkorzeniak.masterdata.api.basic.query.select;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class SelectExpression {

    @Getter
    private List<String> selectExpressionTokens;

    public SelectExpression() {
        this.selectExpressionTokens = new ArrayList<>();
    }
}
