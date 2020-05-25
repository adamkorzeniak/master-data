package com.adamkorzeniak.masterdata.api.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class OrderExpression {

    @Getter
    private List<OrderExpressionToken> orderExpressionTokens;

    public OrderExpression() {
        this.orderExpressionTokens = new ArrayList<>();
    }
}
