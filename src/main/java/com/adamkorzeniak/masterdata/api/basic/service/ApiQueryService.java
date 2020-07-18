package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.api.basic.order.OrderExpression;
import com.adamkorzeniak.masterdata.api.basic.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.basic.select.SelectExpression;

import java.util.Map;

public interface ApiQueryService {

    FilterExpression buildFilterExpression(Map<String, String> map);

    OrderExpression buildOrderExpression(Map<String, String> map);

    SelectExpression buildSelectExpression(Map<String, String> map);
}