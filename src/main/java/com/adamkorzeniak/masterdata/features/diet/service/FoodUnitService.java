package com.adamkorzeniak.masterdata.features.diet.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.adamkorzeniak.masterdata.features.diet.model.FoodUnit;

public interface FoodUnitService {

	List<FoodUnit> searchFoodUnits(Map<String, String> allRequestParams);

	/**
	 * Returns optional of foodUnit for given id. 
	 * If foodUnit not found empty optional
	 * 
	 * @param id Must not be null
	 * 
	 * @return Optional of foodUnit for given id
	 */
	Optional<FoodUnit> findFoodUnitById(Long id);

	/**
	 * Create foodUnit in database.
	 * Returns created foodUnit
	 * 
	 * @param foodUnit Must not be null
	 * 
	 * @return Created foodUnit
	 */
	FoodUnit addFoodUnit(FoodUnit foodUnit);

	/**
	 * Update foodUnit for given id in database.
	 * Returns updated foodUnit
	 * 
	 * @param id Must not be null
	 * @param foodUnit Must not be null
	 * 
	 * @return Updated foodUnit
	 */
	FoodUnit updateFoodUnit(Long id, FoodUnit foodUnit);

	/**
	 * Delete foodUnit for given id in database.
	 * 
	 * @param id Must not be null
	 */
	void deleteFoodUnit(Long id);

	/**
	 * Returns if foodUnit exists in database
	 * 
	 * @param id Must not be null
	 * 
	 * @return If foodUnit exists
	 */
	boolean isFoodUnitExist(Long id);
}
