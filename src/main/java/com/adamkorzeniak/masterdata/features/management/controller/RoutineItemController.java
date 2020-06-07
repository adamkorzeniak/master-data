package com.adamkorzeniak.masterdata.features.management.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.management.model.RoutineItem;
import com.adamkorzeniak.masterdata.features.management.service.RoutineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Management")
public class RoutineItemController {

    private static final String ROUTINE_ITEM_RESOURCE_NAME = "RoutineItems";

    private final RoutineItemService routineService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RoutineItemController(RoutineItemService routineService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.routineService = routineService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of routineItems with 200 OK.
     * <p>
     * If there are no routineItems it returns empty list with 204 No Content
     */
    @GetMapping("/routineItems")
    public ResponseEntity<List<Map<String, Object>>> findRoutineItems(@RequestParam Map<String, String> allRequestParams) {
        List<RoutineItem> routineItems = routineService.searchRoutineItems(allRequestParams);
        if (routineItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(routineItems, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns routineItem with given id with 200 OK.
     * <p>
     * If routineItem with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/routineItems/{routineItemId}")
    public ResponseEntity<RoutineItem> findRoutineItemById(@PathVariable("routineItemId") Long routineItemId) {
        Optional<RoutineItem> routineItem = routineService.findRoutineItemById(routineItemId);
        if (routineItem.isEmpty()) {
            throw new NotFoundException(ROUTINE_ITEM_RESOURCE_NAME, routineItemId);
        }
        return new ResponseEntity<>(routineItem.get(), HttpStatus.OK);
    }

    /**
     * Creates a routineItem in database.
     * Returns created routineItem with 201 Created.
     * <p>
     * If provided routineItem data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/routineItems")
    public ResponseEntity<RoutineItem> addRoutineItem(@RequestBody @Valid RoutineItem routineItem) {
        RoutineItem newRoutineItem = routineService.addRoutineItem(routineItem);
        return new ResponseEntity<>(newRoutineItem, HttpStatus.CREATED);
    }

    /**
     * Updates a routineItem with given id in database. Returns updated routineItem with 200 OK.
     * <p>
     * If routineItem with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided routineItem data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/routineItems/{routineItemId}")
    public ResponseEntity<RoutineItem> updateRoutineItem(@RequestBody @Valid RoutineItem routineItem, @PathVariable Long routineItemId) {
        boolean exists = routineService.isRoutineItemExist(routineItemId);
        if (!exists) {
            throw new NotFoundException(ROUTINE_ITEM_RESOURCE_NAME, routineItemId);
        }
        RoutineItem newRoutineItem = routineService.updateRoutineItem(routineItemId, routineItem);
        return new ResponseEntity<>(newRoutineItem, HttpStatus.OK);
    }

    /**
     * Deletes a routineItem with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If routineItem with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/routineItems/{routineItemId}")
    public ResponseEntity<Void> deleteRoutineItem(@PathVariable Long routineItemId) {
        boolean exists = routineService.isRoutineItemExist(routineItemId);
        if (!exists) {
            throw new NotFoundException(ROUTINE_ITEM_RESOURCE_NAME, routineItemId);
        }
        routineService.deleteRoutineItem(routineItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
