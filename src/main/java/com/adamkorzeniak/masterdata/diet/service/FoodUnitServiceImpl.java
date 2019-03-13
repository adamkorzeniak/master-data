package com.adamkorzeniak.masterdata.diet.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.common.GenericSpecification;
import com.adamkorzeniak.masterdata.common.api.SearchFilter;
import com.adamkorzeniak.masterdata.common.api.SearchFilterService;
import com.adamkorzeniak.masterdata.diet.model.FoodUnit;
import com.adamkorzeniak.masterdata.diet.repository.FoodUnitRepository;

@Service
public class FoodUnitServiceImpl implements FoodUnitService {

	@Autowired
	private FoodUnitRepository foodUnitRepository;
	
	@Autowired
	private SearchFilterService searchFilterService;

	@Override
	public List<FoodUnit> searchFoodUnits(Map<String, String> requestParams) {
		List<SearchFilter> filters = searchFilterService.buildFilters(requestParams, "diet.foodUnits");
		Specification<FoodUnit> spec = new GenericSpecification<>(filters);
		return foodUnitRepository.findAll(spec);
	}

	@Override
	public Optional<FoodUnit> findFoodUnitById(Long id) {
		return foodUnitRepository.findById(id);
	}

	@Override
	public FoodUnit addFoodUnit(FoodUnit foodUnit) {
		foodUnit.setId(-1L);
		return foodUnitRepository.save(foodUnit);
	}

	@Override
	public FoodUnit updateFoodUnit(Long id, FoodUnit foodUnit) {
		foodUnit.setId(id);
		return foodUnitRepository.save(foodUnit);
	}

	@Override
	public void deleteFoodUnit(Long id) {
		foodUnitRepository.deleteById(id);
	}

	@Override
	public boolean isFoodUnitExist(Long id) {
		return foodUnitRepository.existsById(id);
	}
}
