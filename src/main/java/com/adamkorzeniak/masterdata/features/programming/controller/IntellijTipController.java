package com.adamkorzeniak.masterdata.features.programming.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.programming.model.IntellijTip;
import com.adamkorzeniak.masterdata.features.programming.service.IntellijTipService;
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
public class IntellijTipController {

    private static final String INTELLIJ_TIP_RESOURCE_NAME = "IntellijTips";

    private final IntellijTipService intellijTipService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public IntellijTipController(IntellijTipService intellijTipService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.intellijTipService = intellijTipService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of intellijTips with 200 OK.
     * <p>
     * If there are no intellijTips it returns empty list with 204 No Content
     */
    @GetMapping("/intellijTips")
    public ResponseEntity<List<Map<String, Object>>> findIntellijTips(@RequestParam Map<String, String> allRequestParams) {
        List<IntellijTip> intellijTips = intellijTipService.searchIntellijTips(allRequestParams);
        if (intellijTips.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(intellijTips, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns intellijTip with given id with 200 OK.
     * <p>
     * If intellijTip with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/intellijTips/{intellijTipId}")
    public ResponseEntity<IntellijTip> findIntellijTipById(@PathVariable("intellijTipId") Long intellijTipId) {
        Optional<IntellijTip> intellijTip = intellijTipService.findIntellijTipById(intellijTipId);
        if (intellijTip.isEmpty()) {
            throw new NotFoundException(INTELLIJ_TIP_RESOURCE_NAME, intellijTipId);
        }
        return new ResponseEntity<>(intellijTip.get(), HttpStatus.OK);
    }

    /**
     * Creates a intellijTip in database.
     * Returns created intellijTip with 201 Created.
     * <p>
     * If provided intellijTip data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/intellijTips")
    public ResponseEntity<IntellijTip> addIntellijTip(@RequestBody @Valid IntellijTip intellijTip) {
        IntellijTip newIntellijTip = intellijTipService.addIntellijTip(intellijTip);
        return new ResponseEntity<>(newIntellijTip, HttpStatus.CREATED);
    }

    /**
     * Updates a intellijTip with given id in database. Returns updated intellijTip with 200 OK.
     * <p>
     * If intellijTip with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided intellijTip data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/intellijTips/{intellijTipId}")
    public ResponseEntity<IntellijTip> updateIntellijTip(@RequestBody @Valid IntellijTip intellijTip, @PathVariable Long intellijTipId) {
        boolean exists = intellijTipService.isIntellijTipExist(intellijTipId);
        if (!exists) {
            throw new NotFoundException(INTELLIJ_TIP_RESOURCE_NAME, intellijTipId);
        }
        IntellijTip newIntellijTip = intellijTipService.updateIntellijTip(intellijTipId, intellijTip);
        return new ResponseEntity<>(newIntellijTip, HttpStatus.OK);
    }

    /**
     * Deletes a intellijTip with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If intellijTip with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/intellijTips/{intellijTipId}")
    public ResponseEntity<Void> deleteIntellijTip(@PathVariable Long intellijTipId) {
        boolean exists = intellijTipService.isIntellijTipExist(intellijTipId);
        if (!exists) {
            throw new NotFoundException(INTELLIJ_TIP_RESOURCE_NAME, intellijTipId);
        }
        intellijTipService.deleteIntellijTip(intellijTipId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
