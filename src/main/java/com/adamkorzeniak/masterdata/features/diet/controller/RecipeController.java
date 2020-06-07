package com.adamkorzeniak.masterdata.features.diet.controller;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.ApiResponseService;
import com.adamkorzeniak.masterdata.api.select.SelectExpression;
import com.adamkorzeniak.masterdata.exception.exceptions.NotFoundException;
import com.adamkorzeniak.masterdata.features.diet.model.Recipe;
import com.adamkorzeniak.masterdata.features.diet.service.RecipeService;
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
public class RecipeController {

    private static final String RECIPE_RESOURCE_NAME = "Recipe";

    private final RecipeService recipeService;
    private final ApiResponseService apiResponseService;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RecipeController(RecipeService recipeService, ApiResponseService apiResponseService, ApiQueryService apiQueryService) {
        this.recipeService = recipeService;
        this.apiResponseService = apiResponseService;
        this.apiQueryService = apiQueryService;
    }

    /**
     * Returns list of recipes with 200 OK.
     * <p>
     * If there are no recipes it returns empty list with 204 No Content
     */
    @GetMapping("/recipes")
    public ResponseEntity<List<Map<String, Object>>> findRecipes(@RequestParam Map<String, String> allRequestParams) {
        List<Recipe> recipes = recipeService.searchRecipes(allRequestParams);
        if (recipes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        SelectExpression selectExpression = apiQueryService.buildSelectExpression(allRequestParams);
        return new ResponseEntity<>(apiResponseService.buildListResponse(recipes, selectExpression), HttpStatus.OK);
    }

    /**
     * Returns recipe with given id with 200 OK.
     * <p>
     * If recipe with given id does not exist it returns error response with 404 Not Found
     */
    @GetMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> findRecipeById(@PathVariable("recipeId") Long recipeId) {
        Optional<Recipe> recipe = recipeService.findRecipeById(recipeId);
        if (recipe.isEmpty()) {
            throw new NotFoundException(RECIPE_RESOURCE_NAME, recipeId);
        }
        return new ResponseEntity<>(recipe.get(), HttpStatus.OK);
    }

    /**
     * Creates a recipe in database.
     * Returns created recipe with 201 Created.
     * <p>
     * If provided recipe data is invalid it returns 400 Bad Request.
     */
    @PostMapping("/recipes")
    public ResponseEntity<Recipe> addRecipe(@RequestBody @Valid Recipe recipe) {
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    /**
     * Updates a recipe with given id in database. Returns updated recipe with 200 OK.
     * <p>
     * If recipe with given id does not exist it returns error response with 404 Not Found
     * <p>
     * If provided recipe data is invalid it returns 400 Bad Request.
     */
    @PutMapping("/recipes/{recipeId}")
    public ResponseEntity<Recipe> updateRecipe(@RequestBody @Valid Recipe recipe, @PathVariable Long recipeId) {
        boolean exists = recipeService.isRecipeExist(recipeId);
        if (!exists) {
            throw new NotFoundException(RECIPE_RESOURCE_NAME, recipeId);
        }
        Recipe newRecipe = recipeService.updateRecipe(recipeId, recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.OK);
    }

    /**
     * Deletes a recipe with given id.
     * Returns empty response with 204 No Content.
     * <p>
     * If recipe with given id does not exist it returns error response with 404 Not Found
     */
    @DeleteMapping("/recipes/{recipeId}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long recipeId) {
        boolean exists = recipeService.isRecipeExist(recipeId);
        if (!exists) {
            throw new NotFoundException(RECIPE_RESOURCE_NAME, recipeId);
        }
        recipeService.deleteRecipe(recipeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
