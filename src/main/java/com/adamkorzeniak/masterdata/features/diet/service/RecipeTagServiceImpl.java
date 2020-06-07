package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.api.ApiQueryService;
import com.adamkorzeniak.masterdata.api.filter.FilterExpression;
import com.adamkorzeniak.masterdata.api.order.OrderExpression;
import com.adamkorzeniak.masterdata.features.diet.model.RecipeTag;
import com.adamkorzeniak.masterdata.features.diet.repository.RecipeTagRepository;
import com.adamkorzeniak.masterdata.persistence.GenericSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeTagServiceImpl implements RecipeTagService {

    private final RecipeTagRepository recipeTagRepository;
    private final ApiQueryService apiQueryService;

    @Autowired
    public RecipeTagServiceImpl(RecipeTagRepository recipeTagRepository,
                              ApiQueryService apiQueryService) {
        this.recipeTagRepository = recipeTagRepository;
        this.apiQueryService = apiQueryService;
    }

    @Override
    public List<RecipeTag> searchRecipeTags(Map<String, String> queryParams) {
        FilterExpression filterExpression = apiQueryService.buildFilterExpression(queryParams);
        OrderExpression orderExpression = apiQueryService.buildOrderExpression(queryParams);
        Specification<RecipeTag> spec = new GenericSpecification<>(filterExpression, orderExpression);
        return recipeTagRepository.findAll(spec);
    }

    @Override
    public Optional<RecipeTag> findRecipeTagById(Long id) {
        return recipeTagRepository.findById(id);
    }

    @Override
    public RecipeTag addRecipeTag(RecipeTag recipeTag) {
        recipeTag.setId(-1L);
        return recipeTagRepository.save(recipeTag);
    }

    @Override
    public RecipeTag updateRecipeTag(Long id, RecipeTag recipeTag) {
        recipeTag.setId(id);
        return recipeTagRepository.save(recipeTag);
    }

    @Override
    public void deleteRecipeTag(Long id) {
        recipeTagRepository.deleteById(id);
    }

    @Override
    public boolean isRecipeTagExist(Long id) {
        return recipeTagRepository.existsById(id);
    }
}
