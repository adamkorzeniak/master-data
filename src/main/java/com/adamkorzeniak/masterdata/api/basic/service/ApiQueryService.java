package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.api.basic.query.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.basic.query.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.basic.query.select.SelectExpression;

import java.util.Map;

public interface ApiQueryService {

    FilterExpression buildFilterExpression(Map<String, String> map);

    OrderExpression buildOrderExpression(Map<String, String> map);

    SelectExpression buildSelectExpression(Map<String, String> map);
}