package com.adamkorzeniak.masterdata.template;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.qwertyP.model.Qwerty;
import com.adamkorzeniak.masterdata.features.qwertyP.service.QwertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/QwertyP")
public class QwertyController {

    private static final String QWERTY_RESOURCE_NAME = "Qwerty";

    private final QwertyService qwertyService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public QwertyController(QwertyService qwertyService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.qwertyService = qwertyService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of qwertys with 200 OK.
     * <p>
     * If there are no qwertys it returns empty list with 204 No Content
     */
    @GetMapping("/qwertys")
    public ResponseEntity<List<Map<String, Object>>> findQwertys(@RequestParam Map<String, String> allRequestParams) {
        List<Qwerty> qwertys = qwertyService.searchQwertys(allRequestParams);
        if (qwertys.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(qwertys, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns qwerty with given id with 200 OK.
     * <p>
     * If qwerty with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/qwertys/{qwertyId}")
    public ResponseEntity<Qwerty> findQwertyById(@PathVariable("qwertyId") Long qwertyId) {
        Optional<Qwerty> qwerty = qwertyService.findQwertyById(qwertyId);
        if (qwerty.isEmpty()) {
            throw new NotFoundException(QWERTY_RESOURCE_NAME, qwertyId);
        }
        return new ResponseEntity<>(qwerty.get(), HttpStatus.OK);
    }

    /**
     * Creates a qwerty in database.
     * Returns created qwerty with 201 Created.
     * <p>
     * If provided qwerty data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/qwertys")
    public ResponseEntity<Qwerty> addQwerty(@RequestBody @Valid Qwerty qwerty) {
        Qwerty newQwerty = qwertyService.addQwerty(qwerty);
        return new ResponseEntity<>(newQwerty, HttpStatus.CREATED);
    }

    /**
     * Updates a qwerty with given id in database. Returns updated qwerty with 200 OK.
     * <p>
     * If qwerty with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided qwerty data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/qwertys/{qwertyId}")
    public ResponseEntity<Qwerty> updateQwerty(@RequestBody @Valid Qwerty qwerty, @PathVariable Long qwertyId) {
        boolean exists = qwertyService.isQwertyExist(qwertyId);
        if (!exists) {
            throw new NotFoundException(QWERTY_RESOURCE_NAME, qwertyId);
        }
        Qwerty newQwerty = qwertyService.updateQwerty(qwertyId, qwerty);
        return new ResponseEntity<>(newQwerty, HttpStatus.OK);
    }

    /**
     * Deletes a qwerty with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If qwerty with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/qwertys/{qwertyId}")
    public ResponseEntity<Void> deleteQwerty(@PathVariable Long qwertyId) {
        boolean exists = qwertyService.isQwertyExist(qwertyId);
        if (!exists) {
            throw new NotFoundException(QWERTY_RESOURCE_NAME, qwertyId);
        }
        qwertyService.deleteQwerty(qwertyId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
