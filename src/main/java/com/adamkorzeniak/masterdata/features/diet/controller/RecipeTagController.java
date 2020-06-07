package com.adamkorzeniak.masterdata.features.diet.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.diet.model.RecipeTag;
import com.adamkorzeniak.masterdata.features.diet.service.RecipeTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v0/Diet")
public class RecipeTagController {

    private static final String RECIPE_TAG_RESOURCE_NAME = "RecipeTag";

    private final RecipeTagService recipeTagService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RecipeTagController(RecipeTagService recipeTagService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.recipeTagService = recipeTagService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of recipeTags with 200 OK.
     * <p>
     * If there are no recipeTags it returns empty list with 204 No Content
     */
    @GetMapping("/tags")
    public ResponseEntity<List<Map<String, Object>>> findRecipeTags(@RequestParam Map<String, String> allRequestParams) {
        List<RecipeTag> recipeTags = recipeTagService.searchRecipeTags(allRequestParams);
        if (recipeTags.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(recipeTags, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns recipeTag with given id with 200 OK.
     * <p>
     * If recipeTag with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/tags/{recipeTagId}")
    public ResponseEntity<RecipeTag> findRecipeTagById(@PathVariable("recipeTagId") Long recipeTagId) {
        Optional<RecipeTag> recipeTag = recipeTagService.findRecipeTagById(recipeTagId);
        if (recipeTag.isEmpty()) {
            throw new NotFoundException(RECIPE_TAG_RESOURCE_NAME, recipeTagId);
        }
        return new ResponseEntity<>(recipeTag.get(), HttpStatus.OK);
    }

    /**
     * Creates a recipeTag in database.
     * Returns created recipeTag with 201 Created.
     * <p>
     * If provided recipeTag data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/tags")
    public ResponseEntity<RecipeTag> addRecipeTag(@RequestBody @Valid RecipeTag recipeTag) {
        RecipeTag newRecipeTag = recipeTagService.addRecipeTag(recipeTag);
        return new ResponseEntity<>(newRecipeTag, HttpStatus.CREATED);
    }

    /**
     * Updates a recipeTag with given id in database. Returns updated recipeTag with 200 OK.
     * <p>
     * If recipeTag with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided recipeTag data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/tags/{recipeTagId}")
    public ResponseEntity<RecipeTag> updateRecipeTag(@RequestBody @Valid RecipeTag recipeTag, @PathVariable Long recipeTagId) {
        boolean exists = recipeTagService.isRecipeTagExist(recipeTagId);
        if (!exists) {
            throw new NotFoundException(RECIPE_TAG_RESOURCE_NAME, recipeTagId);
        }
        RecipeTag newRecipeTag = recipeTagService.updateRecipeTag(recipeTagId, recipeTag);
        return new ResponseEntity<>(newRecipeTag, HttpStatus.OK);
    }

    /**
     * Deletes a recipeTag with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If recipeTag with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/tags/{recipeTagId}")
    public ResponseEntity<Void> deleteRecipeTag(@PathVariable Long recipeTagId) {
        boolean exists = recipeTagService.isRecipeTagExist(recipeTagId);
        if (!exists) {
            throw new NotFoundException(RECIPE_TAG_RESOURCE_NAME, recipeTagId);
        }
        recipeTagService.deleteRecipeTag(recipeTagId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
