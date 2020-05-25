package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.filter.FilterExpressionToken;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpressionToken;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiQueryServiceImpl implements ApiQueryService {

    private static final String FILTER_QUERY_PARAM = "filter";
    private static final String PREDICATE_JOINER = " and ";
    private static final String ORDER_QUERY_PARAM = "order";
    private static final String SORT_JOINER = ", ";

    @Override
    public FilterExpression buildFilterExpression(Map<String, String> map) {
        String expression = map.get(FILTER_QUERY_PARAM);
        if (expression == null) {
            return new FilterExpression();
        }
        List<FilterExpressionToken> predicates = Arrays.stream(expression.split(PREDICATE_JOINER))
                .map(this::buildFilterExpressionToken)
                .collect(Collectors.toList());
        return new FilterExpression(predicates);
    }

    @Override
    public OrderExpression buildOrderExpression(Map<String, String> map) {
        String expression = map.get(ORDER_QUERY_PARAM);
        if (expression == null) {
            return new OrderExpression();
        }
        List<OrderExpressionToken> predicates = Arrays.stream(expression.split(SORT_JOINER))
                .map(this::buildOrderExpressionToken)
                .collect(Collectors.toList());
        return new OrderExpression(predicates);
    }

    private FilterExpressionToken buildFilterExpressionToken(String s) {
        return new FilterExpressionToken(s);
    }

    private OrderExpressionToken buildOrderExpressionToken(String s) {
        return new OrderExpressionToken(s);
    }
}
