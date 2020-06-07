package com.adamkorzeniak.masterdata.api;

import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ApiResponseServiceImpl implements ApiResponseService {

    private final ObjectMapper mapper;

    @Autowired
    public ApiResponseServiceImpl(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Map<String, Object>> buildListResponse(List<?> response, SelectExpression selectExpression) {
        return response.stream().map(item -> buildObjectResponse(item, selectExpression)).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> buildObjectResponse(Object response, SelectExpression selectExpression) {
        Map<String, Object> map = mapper.convertValue(response, new TypeReference<Map<String, Object>>() {});
        List<String> selectTokens = selectExpression.getSelectExpressionTokens();
        if (selectTokens.isEmpty()) {
            return map;
        }
        Map<String, Object> resultMap = new LinkedHashMap<>();
        for (String token : selectTokens) {
            Object entry = map.get(token);
            resultMap.put(token, entry);
        }
        return resultMap;
    }
}
