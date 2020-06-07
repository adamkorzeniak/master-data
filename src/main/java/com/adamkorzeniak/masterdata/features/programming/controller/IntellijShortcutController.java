package com.adamkorzeniak.masterdata.features.programming.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.programming.model.IntellijShortcut;
import com.adamkorzeniak.masterdata.features.programming.service.IntellijShortcutService;
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
public class IntellijShortcutController {

    private static final String INTELLIJ_SHORTCUT_RESOURCE_NAME = "IntellijShortcuts";

    private final IntellijShortcutService intellijShortcutService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public IntellijShortcutController(IntellijShortcutService intellijShortcutService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.intellijShortcutService = intellijShortcutService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of intellijShortcuts with 200 OK.
     * <p>
     * If there are no intellijShortcuts it returns empty list with 204 No Content
     */
    @GetMapping("/intellijShortcuts")
    public ResponseEntity<List<Map<String, Object>>> findIntellijShortcuts(@RequestParam Map<String, String> allRequestParams) {
        List<IntellijShortcut> intellijShortcuts = intellijShortcutService.searchIntellijShortcuts(allRequestParams);
        if (intellijShortcuts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(intellijShortcuts, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns intellijShortcut with given id with 200 OK.
     * <p>
     * If intellijShortcut with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/intellijShortcuts/{intellijShortcutId}")
    public ResponseEntity<IntellijShortcut> findIntellijShortcutById(@PathVariable("intellijShortcutId") Long intellijShortcutId) {
        Optional<IntellijShortcut> intellijShortcut = intellijShortcutService.findIntellijShortcutById(intellijShortcutId);
        if (intellijShortcut.isEmpty()) {
            throw new NotFoundException(INTELLIJ_SHORTCUT_RESOURCE_NAME, intellijShortcutId);
        }
        return new ResponseEntity<>(intellijShortcut.get(), HttpStatus.OK);
    }

    /**
     * Creates a intellijShortcut in database.
     * Returns created intellijShortcut with 201 Created.
     * <p>
     * If provided intellijShortcut data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/intellijShortcuts")
    public ResponseEntity<IntellijShortcut> addIntellijShortcut(@RequestBody @Valid IntellijShortcut intellijShortcut) {
        IntellijShortcut newIntellijShortcut = intellijShortcutService.addIntellijShortcut(intellijShortcut);
        return new ResponseEntity<>(newIntellijShortcut, HttpStatus.CREATED);
    }

    /**
     * Updates a intellijShortcut with given id in database. Returns updated intellijShortcut with 200 OK.
     * <p>
     * If intellijShortcut with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided intellijShortcut data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/intellijShortcuts/{intellijShortcutId}")
    public ResponseEntity<IntellijShortcut> updateIntellijShortcut(@RequestBody @Valid IntellijShortcut intellijShortcut, @PathVariable Long intellijShortcutId) {
        boolean exists = intellijShortcutService.isIntellijShortcutExist(intellijShortcutId);
        if (!exists) {
            throw new NotFoundException(INTELLIJ_SHORTCUT_RESOURCE_NAME, intellijShortcutId);
        }
        IntellijShortcut newIntellijShortcut = intellijShortcutService.updateIntellijShortcut(intellijShortcutId, intellijShortcut);
        return new ResponseEntity<>(newIntellijShortcut, HttpStatus.OK);
    }

    /**
     * Deletes a intellijShortcut with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If intellijShortcut with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/intellijShortcuts/{intellijShortcutId}")
    public ResponseEntity<Void> deleteIntellijShortcut(@PathVariable Long intellijShortcutId) {
        boolean exists = intellijShortcutService.isIntellijShortcutExist(intellijShortcutId);
        if (!exists) {
            throw new NotFoundException(INTELLIJ_SHORTCUT_RESOURCE_NAME, intellijShortcutId);
        }
        intellijShortcutService.deleteIntellijShortcut(intellijShortcutId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
