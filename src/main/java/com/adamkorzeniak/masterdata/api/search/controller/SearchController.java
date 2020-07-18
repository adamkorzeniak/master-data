package com.adamkorzeniak.masterdata.api.search.controller;

import com.adamkorzeniak.masterdata.api.basic.service.ApiQueryService;
import com.adamkorzeniak.masterdata.api.basic.service.ApiResponseService;
import com.adamkorzeniak.masterdata.api.basic.query.select.SelectExpression;
import com.adamkorzeniak.masterdata.api.search.model.SearchResult;
import com.adamkorzeniak.masterdata.api.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v0/Search")
public class SearchController {

    private final SearchService searchService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public SearchController(SearchService searchService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.searchService = searchService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    @GetMapping("")
    public ResponseEntity<List<Map<String, Object>>> searchAll(@RequestParam Map<String, String> allRequestParams) {
        List<SearchResult> results = searchService.searchAll(allRequestParams.get("search"));
        if (results.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(results, selectExpression), HttpStatus.OK);
    }
}
