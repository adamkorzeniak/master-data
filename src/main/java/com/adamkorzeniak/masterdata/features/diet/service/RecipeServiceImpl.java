package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.diet.model.Recipe;
import com.adamkorzeniak.masterdata.features.diet.repository.RecipeRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                           ApiQueryService apiQueryService) {
        this.recipeRepository = recipeRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<Recipe> searchRecipes(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<Recipe> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return recipeRepository.findAll(spec);
    }

    @Override
    public Optional<Recipe> findRecipeById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Recipe addRecipe(Recipe recipe) {
        recipe.setId(-1L);
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe updateRecipe(Long id, Recipe recipe) {
        recipe.setId(id);
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public boolean isRecipeExist(Long id) {
        return recipeRepository.existsById(id);
    }
}
