package com.adamkorzeniak.masterdata.features.management.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.management.model.LearningTechnique;
import com.adamkorzeniak.masterdata.features.management.service.LearningTechniqueService;
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
public class LearningTechniqueController {

    private static final String LEARNING_TECHNIQUE_RESOURCE_NAME = "LearningTechniques";

    private final LearningTechniqueService routineService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public LearningTechniqueController(LearningTechniqueService routineService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.routineService = routineService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of learningTechniques with 200 OK.
     * <p>
     * If there are no learningTechniques it returns empty list with 204 No Content
     */
    @GetMapping("/learningTechniques")
    public ResponseEntity<List<Map<String, Object>>> findLearningTechniques(@RequestParam Map<String, String> allRequestParams) {
        List<LearningTechnique> learningTechniques = routineService.searchLearningTechniques(allRequestParams);
        if (learningTechniques.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(learningTechniques, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns learningTechnique with given id with 200 OK.
     * <p>
     * If learningTechnique with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/learningTechniques/{learningTechniqueId}")
    public ResponseEntity<LearningTechnique> findLearningTechniqueById(@PathVariable("learningTechniqueId") Long learningTechniqueId) {
        Optional<LearningTechnique> learningTechnique = routineService.findLearningTechniqueById(learningTechniqueId);
        if (learningTechnique.isEmpty()) {
            throw new NotFoundException(LEARNING_TECHNIQUE_RESOURCE_NAME, learningTechniqueId);
        }
        return new ResponseEntity<>(learningTechnique.get(), HttpStatus.OK);
    }

    /**
     * Creates a learningTechnique in database.
     * Returns created learningTechnique with 201 Created.
     * <p>
     * If provided learningTechnique data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/learningTechniques")
    public ResponseEntity<LearningTechnique> addLearningTechnique(@RequestBody @Valid LearningTechnique learningTechnique) {
        LearningTechnique newLearningTechnique = routineService.addLearningTechnique(learningTechnique);
        return new ResponseEntity<>(newLearningTechnique, HttpStatus.CREATED);
    }

    /**
     * Updates a learningTechnique with given id in database. Returns updated learningTechnique with 200 OK.
     * <p>
     * If learningTechnique with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided learningTechnique data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/learningTechniques/{learningTechniqueId}")
    public ResponseEntity<LearningTechnique> updateLearningTechnique(@RequestBody @Valid LearningTechnique learningTechnique, @PathVariable Long learningTechniqueId) {
        boolean exists = routineService.isLearningTechniqueExist(learningTechniqueId);
        if (!exists) {
            throw new NotFoundException(LEARNING_TECHNIQUE_RESOURCE_NAME, learningTechniqueId);
        }
        LearningTechnique newLearningTechnique = routineService.updateLearningTechnique(learningTechniqueId, learningTechnique);
        return new ResponseEntity<>(newLearningTechnique, HttpStatus.OK);
    }

    /**
     * Deletes a learningTechnique with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If learningTechnique with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/learningTechniques/{learningTechniqueId}")
    public ResponseEntity<Void> deleteLearningTechnique(@PathVariable Long learningTechniqueId) {
        boolean exists = routineService.isLearningTechniqueExist(learningTechniqueId);
        if (!exists) {
            throw new NotFoundException(LEARNING_TECHNIQUE_RESOURCE_NAME, learningTechniqueId);
        }
        routineService.deleteLearningTechnique(learningTechniqueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
