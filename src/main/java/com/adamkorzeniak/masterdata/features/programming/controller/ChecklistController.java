package com.adamkorzeniak.masterdata.features.programming.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.programming.model.ChecklistGroup;
import com.adamkorzeniak.masterdata.features.programming.service.ChecklistGroupService;
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
public class ChecklistGroupController {

    private static final String CHECKLIST_RESOURCE_NAME = "Checklists";

    private final ChecklistGroupService checklistGroupService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public ChecklistGroupController(ChecklistGroupService checklistGroupService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.checklistGroupService = checklistGroupService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of checklistGroups with 200 OK.
     * <p>
     * If there are no checklistGroups it returns empty list with 204 No Content
     */
    @GetMapping("/checklist")
    public ResponseEntity<List<Map<String, Object>>> findChecklistGroups(@RequestParam Map<String, String> allRequestParams) {
        List<ChecklistGroup> checklistGroups = checklistGroupService.searchChecklistGroups(allRequestParams);
        if (checklistGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(checklistGroups, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns checklistGroup with given id with 200 OK.
     * <p>
     * If checklistGroup with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/checklist/{checklistGroupId}")
    public ResponseEntity<ChecklistGroup> findChecklistGroupById(@PathVariable("checklistGroupId") Long checklistGroupId) {
        Optional<ChecklistGroup> checklistGroup = checklistGroupService.findChecklistGroupById(checklistGroupId);
        if (checklistGroup.isEmpty()) {
            throw new NotFoundException(CHECKLIST_RESOURCE_NAME, checklistGroupId);
        }
        return new ResponseEntity<>(checklistGroup.get(), HttpStatus.OK);
    }

    /**
     * Creates a checklistGroup in database.
     * Returns created checklistGroup with 201 Created.
     * <p>
     * If provided checklistGroup data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/checklist")
    public ResponseEntity<ChecklistGroup> addChecklistGroup(@RequestBody @Valid ChecklistGroup checklistGroup) {
        ChecklistGroup newChecklistGroup = checklistGroupService.addChecklistGroup(checklistGroup);
        return new ResponseEntity<>(newChecklistGroup, HttpStatus.CREATED);
    }

    /**
     * Updates a checklistGroup with given id in database. Returns updated checklistGroup with 200 OK.
     * <p>
     * If checklistGroup with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided checklistGroup data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/checklist/{checklistGroupId}")
    public ResponseEntity<ChecklistGroup> updateChecklistGroup(@RequestBody @Valid ChecklistGroup checklistGroup, @PathVariable Long checklistGroupId) {
        boolean exists = checklistGroupService.isChecklistGroupExist(checklistGroupId);
        if (!exists) {
            throw new NotFoundException(CHECKLIST_RESOURCE_NAME, checklistGroupId);
        }
        ChecklistGroup newChecklistGroup = checklistGroupService.updateChecklistGroup(checklistGroupId, checklistGroup);
        return new ResponseEntity<>(newChecklistGroup, HttpStatus.OK);
    }

    /**
     * Deletes a checklistGroup with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If checklistGroup with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/checklist/{checklistGroupId}")
    public ResponseEntity<Void> deleteChecklistGroup(@PathVariable Long checklistGroupId) {
        boolean exists = checklistGroupService.isChecklistGroupExist(checklistGroupId);
        if (!exists) {
            throw new NotFoundException(CHECKLIST_RESOURCE_NAME, checklistGroupId);
        }
        checklistGroupService.deleteChecklistGroup(checklistGroupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
