package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.features.diet.model.Recipe;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeService {

    List<Recipe> searchRecipes(Map<String, String> allRequestParams);

    Optional<Recipe> findRecipeById(Long id);

    Recipe addRecipe(Recipe recipe);

    Recipe updateRecipe(Long id, Recipe recipe);

    void deleteRecipe(Long id);

    boolean isRecipeExist(Long id);
}
