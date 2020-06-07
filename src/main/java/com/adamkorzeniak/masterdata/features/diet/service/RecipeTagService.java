package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.features.diet.model.RecipeTag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RecipeTagService {

    List<RecipeTag> searchRecipeTags(Map<String, String> allRequestParams);

    Optional<RecipeTag> findRecipeTagById(Long id);

    RecipeTag addRecipeTag(RecipeTag recipeTag);

    RecipeTag updateRecipeTag(Long id, RecipeTag recipeTag);

    void deleteRecipeTag(Long id);

    boolean isRecipeTagExist(Long id);
}
