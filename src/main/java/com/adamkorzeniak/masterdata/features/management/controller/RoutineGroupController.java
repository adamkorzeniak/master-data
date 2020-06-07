package com.adamkorzeniak.masterdata.features.management.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.management.model.RoutineGroup;
import com.adamkorzeniak.masterdata.features.management.service.RoutineGroupService;
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
public class RoutineGroupController {

    private static final String ROUTINE_RESOURCE_NAME = "Routines";

    private final RoutineGroupService routineService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RoutineGroupController(RoutineGroupService routineService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.routineService = routineService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of routineGroups with 200 OK.
     * <p>
     * If there are no routineGroups it returns empty list with 204 No Content
     */
    @GetMapping("/routineGroups")
    public ResponseEntity<List<Map<String, Object>>> findRoutineGroups(@RequestParam Map<String, String> allRequestParams) {
        List<RoutineGroup> routineGroups = routineService.searchRoutineGroups(allRequestParams);
        if (routineGroups.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(routineGroups, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns routineGroup with given id with 200 OK.
     * <p>
     * If routineGroup with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/routineGroups/{routineGroupId}")
    public ResponseEntity<RoutineGroup> findRoutineGroupById(@PathVariable("routineGroupId") Long routineGroupId) {
        Optional<RoutineGroup> routineGroup = routineService.findRoutineGroupById(routineGroupId);
        if (routineGroup.isEmpty()) {
            throw new NotFoundException(ROUTINE_RESOURCE_NAME, routineGroupId);
        }
        return new ResponseEntity<>(routineGroup.get(), HttpStatus.OK);
    }

    /**
     * Creates a routineGroup in database.
     * Returns created routineGroup with 201 Created.
     * <p>
     * If provided routineGroup data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/routineGroups")
    public ResponseEntity<RoutineGroup> addRoutineGroup(@RequestBody @Valid RoutineGroup routineGroup) {
        RoutineGroup newRoutineGroup = routineService.addRoutineGroup(routineGroup);
        return new ResponseEntity<>(newRoutineGroup, HttpStatus.CREATED);
    }

    /**
     * Updates a routineGroup with given id in database. Returns updated routineGroup with 200 OK.
     * <p>
     * If routineGroup with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided routineGroup data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/routineGroups/{routineGroupId}")
    public ResponseEntity<RoutineGroup> updateRoutineGroup(@RequestBody @Valid RoutineGroup routineGroup, @PathVariable Long routineGroupId) {
        boolean exists = routineService.isRoutineGroupExist(routineGroupId);
        if (!exists) {
            throw new NotFoundException(ROUTINE_RESOURCE_NAME, routineGroupId);
        }
        RoutineGroup newRoutineGroup = routineService.updateRoutineGroup(routineGroupId, routineGroup);
        return new ResponseEntity<>(newRoutineGroup, HttpStatus.OK);
    }

    /**
     * Deletes a routineGroup with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If routineGroup with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/routineGroups/{routineGroupId}")
    public ResponseEntity<Void> deleteRoutineGroup(@PathVariable Long routineGroupId) {
        boolean exists = routineService.isRoutineGroupExist(routineGroupId);
        if (!exists) {
            throw new NotFoundException(ROUTINE_RESOURCE_NAME, routineGroupId);
        }
        routineService.deleteRoutineGroup(routineGroupId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
