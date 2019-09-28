package com.adamkorzeniak.masterdata.features.movie.service;

import com.adamkorzeniak.masterdata.features.movie.model.Genre;
import com.adamkorzeniak.masterdata.features.movie.model.dto.GenreDTO;

public class GenreServiceHelper {

    private GenreServiceHelper() {}

    /**
     * Converts Genre DTO to Entity.
     */
    public static Genre convertToEntity(GenreDTO dto) {
        Genre entity = new Genre();
        entity.setId(dto.getId());
        entity.setName(dto.getName());

        return entity;
    }

    /**
     * Converts Genre Entity to DTO.
     */
    public static GenreDTO convertToDTO(Genre entity) {
        GenreDTO dto = new GenreDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());

        return dto;
    }
}
