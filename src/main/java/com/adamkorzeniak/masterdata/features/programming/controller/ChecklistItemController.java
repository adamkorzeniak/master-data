package com.adamkorzeniak.masterdata.features.programming.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.programming.model.ChecklistItem;
import com.adamkorzeniak.masterdata.features.programming.service.ChecklistItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Programming")
public class ChecklistItemController {

    private static final String CHECKLIST_ITEM_RESOURCE_NAME = "ChecklistItems";

    private final ChecklistItemService checklistService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ChecklistItemController(ChecklistItemService checklistService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.checklistService = checklistService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of checklistItems with 200 OK.
     * <p>
     * If there are no checklistItems it returns empty list with 204 No Content
     */
    @GetMapping("/checklistItems")
    public ResponseEntity<List<Map<String, Object>>> findChecklistItems(@RequestParam Map<String, String> allRequestParams) {
        List<ChecklistItem> checklistItems = checklistService.searchChecklistItems(allRequestParams);
        if (checklistItems.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(checklistItems, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns checklistItem with given id with 200 OK.
     * <p>
     * If checklistItem with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/checklistItems/{checklistItemId}")
    public ResponseEntity<ChecklistItem> findChecklistItemById(@PathVariable("checklistItemId") Long checklistItemId) {
        Optional<ChecklistItem> checklistItem = checklistService.findChecklistItemById(checklistItemId);
        if (checklistItem.isEmpty()) {
            throw new NotFoundException(CHECKLIST_ITEM_RESOURCE_NAME, checklistItemId);
        }
        return new ResponseEntity<>(checklistItem.get(), HttpStatus.OK);
    }

    /**
     * Creates a checklistItem in database.
     * Returns created checklistItem with 201 Created.
     * <p>
     * If provided checklistItem data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/checklistItems")
    public ResponseEntity<ChecklistItem> addChecklistItem(@RequestBody @Valid ChecklistItem checklistItem) {
        ChecklistItem newChecklistItem = checklistService.addChecklistItem(checklistItem);
        return new ResponseEntity<>(newChecklistItem, HttpStatus.CREATED);
    }

    /**
     * Updates a checklistItem with given id in database. Returns updated checklistItem with 200 OK.
     * <p>
     * If checklistItem with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided checklistItem data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/checklistItems/{checklistItemId}")
    public ResponseEntity<ChecklistItem> updateChecklistItem(@RequestBody @Valid ChecklistItem checklistItem, @PathVariable Long checklistItemId) {
        boolean exists = checklistService.isChecklistItemExist(checklistItemId);
        if (!exists) {
            throw new NotFoundException(CHECKLIST_ITEM_RESOURCE_NAME, checklistItemId);
        }
        ChecklistItem newChecklistItem = checklistService.updateChecklistItem(checklistItemId, checklistItem);
        return new ResponseEntity<>(newChecklistItem, HttpStatus.OK);
    }

    /**
     * Deletes a checklistItem with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If checklistItem with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/checklistItems/{checklistItemId}")
    public ResponseEntity<Void> deleteChecklistItem(@PathVariable Long checklistItemId) {
        boolean exists = checklistService.isChecklistItemExist(checklistItemId);
        if (!exists) {
            throw new NotFoundException(CHECKLIST_ITEM_RESOURCE_NAME, checklistItemId);
        }
        checklistService.deleteChecklistItem(checklistItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
