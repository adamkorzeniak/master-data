package com.adamkorzeniak.masterdata.api.basic.service;

import com.adamkorzeniak.masterdata.api.basic.query.select.SelectExpression;

import java.util.List;
import java.util.Map;

public interface ApiResponseService {

    List<Map<String, Object>> buildListResponse(List<?> response, SelectExpression selectExpression);

    Map<String, Object> buildObjectResponse(Object response, SelectExpression selectExpression);
}