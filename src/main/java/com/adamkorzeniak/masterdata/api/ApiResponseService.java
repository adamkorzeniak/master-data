package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.api.select.SelectExpression;

import java.util.List;
import java.util.Map;

public interface ApiResponseService {

    List<Map<String, Object>> buildListResponse(List<?> response, SelectExpression selectExpression);

    Map<String, Object> buildObjectResponse(Object response, SelectExpression selectExpression);
}