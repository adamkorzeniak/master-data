package com.adamkorzeniak.masterdata.features.diet.service;

import com.adamkorzeniak.masterdata.features.diet.model.Product;
import com.adamkorzeniak.masterdata.features.diet.model.UnitType;
import com.adamkorzeniak.masterdata.features.diet.model.dto.ProductDTO;

public class ProductServiceHelper {

    private ProductServiceHelper() {
    }

    /**
     * Converts Product DTO to Entity.
     *
     * @param dto Must not be null.
     * @return Product entity
     */
    public static Product convertToEntity(ProductDTO dto) {
        Product entity = new Product();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCharacteristic(dto.getCharacteristic());
        entity.setBaseUnit(UnitType.valueOf(dto.getBaseUnit().toUpperCase()));
        entity.setCalories(dto.getCalories());
        entity.setCarbs(dto.getCarbs());
        entity.setFats(dto.getFats());
        entity.setProteins(dto.getProteins());
        entity.setRoughage(dto.getRoughage());
        entity.setSalt(dto.getSalt());

        return entity;
    }

    /**
     * Converts Product Entity to DTO.
     *
     * @param entity Must not be null.
     * @return Product DTO
     */
    public static ProductDTO convertToDTO(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCharacteristic(entity.getCharacteristic());
        dto.setBaseUnit(entity.getBaseUnit().toString());
        dto.setCalories(entity.getCalories());
        dto.setCarbs(entity.getCarbs());
        dto.setFats(entity.getFats());
        dto.setProteins(entity.getProteins());
        dto.setRoughage(entity.getRoughage());
        dto.setSalt(entity.getSalt());

        return dto;
    }
}
