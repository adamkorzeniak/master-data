package com.adamkorzeniak.masterdata.diet.service;

import com.adamkorzeniak.masterdata.diet.model.FoodUnit;
import com.adamkorzeniak.masterdata.diet.model.dto.FoodUnitDTO;

public class FoodServiceHelper {

	private FoodServiceHelper() {}

	/**
	 * Converts FoodUnit DTO to Entity.
	 * 
	 * @param dto Must not be null.
	 * 
	 * @return FoodUnit entity
	 */
	public static FoodUnit convertToEntity(FoodUnitDTO dto) {
		FoodUnit entity = new FoodUnit();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setMultiplier(dto.getMultiplier());

		return entity;
	}

	/**
	 * Converts FoodUnit Entity to DTO.
	 * 
	 * @param entity Must not be null.
	 * 
	 * @return FoodUnit DTO
	 */
	public static FoodUnitDTO convertToDTO(FoodUnit entity) {
		FoodUnitDTO dto = new FoodUnitDTO();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setMultiplier(entity.getMultiplier());

		return dto;
	}
}
