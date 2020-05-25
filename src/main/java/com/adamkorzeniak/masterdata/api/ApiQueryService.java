package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;

import java.util.Map;

public interface ApiQueryService {

    FilterExpression buildFilterExpression(Map<String, String> map);

    OrderExpression buildOrderExpression(Map<String, String> map);
}