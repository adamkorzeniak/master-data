package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpressionToken;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpressionToken;
import com.adamkorzeniak.masterdata.api.basic.query.select.SelectExpression;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiQueryServiceImpl implements ApiQueryService {

    private static final String FILTER_QUERY_PARAM = "filter";
    private static final String ORDER_QUERY_PARAM = "order";
    private static final String SELECT_QUERY_PARAM = "select";
    private static final String PREDICATE_JOINER = " and ";
    private static final String SORT_JOINER = ",";
    private static final String SELECT_JOINER = ",";

    @Override
    public FilterExpression buildFilterExpression(Map<String, String> map) {
        String expression = map.get(FILTER_QUERY_PARAM);
        if (expression == null || expression.isBlank()) {
            return new FilterExpression();
        }
        List<FilterExpressionToken> expressionTokens = Arrays.stream(expression.split(PREDICATE_JOINER))
                .map(this::buildFilterExpressionToken)
                .collect(Collectors.toList());
        return new FilterExpression(expressionTokens);
    }

    @Override
    public OrderExpression buildOrderExpression(Map<String, String> map) {
        String expression = map.get(ORDER_QUERY_PARAM);
        if (expression == null || expression.isBlank()) {
            return new OrderExpression();
        }
        List<OrderExpressionToken> expressionTokens = Arrays.stream(expression.split(SORT_JOINER))
                .map(this::buildOrderExpressionToken)
                .collect(Collectors.toList());
        return new OrderExpression(expressionTokens);
    }

    @Override
    public SelectExpression buildSelectExpression(Map<String, String> map) {
        String expression = map.get(SELECT_QUERY_PARAM);
        if (expression == null || expression.isBlank()) {
            return new SelectExpression();
        }
        List<String> expressionTokens = Arrays.stream(expression.split(SELECT_JOINER))
                .map(String::trim)
                .collect(Collectors.toList());
        return new SelectExpression(expressionTokens);
    }

    private FilterExpressionToken buildFilterExpressionToken(String s) {
        return new FilterExpressionToken(s);
    }

    private OrderExpressionToken buildOrderExpressionToken(String s) {
        return new OrderExpressionToken(s);
    }
}
