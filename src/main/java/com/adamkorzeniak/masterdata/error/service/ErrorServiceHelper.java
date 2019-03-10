package com.adamkorzeniak.masterdata.error.service;

import com.adamkorzeniak.masterdata.error.model.Error;
import com.adamkorzeniak.masterdata.error.model.ErrorDTO;

public class ErrorServiceHelper {

	private ErrorServiceHelper() {}

	/**
	 * Converts Error DTO to Entity.
	 * 
	 * @param dto Must not be null.
	 * 
	 * @return Error entity
	 */
	public static Error convertToEntity(ErrorDTO dto) {
		Error entity = new Error();
		entity.setId(dto.getId());
		entity.setErrorId(dto.getErrorId());
		entity.setAppId(dto.getAppId());
		entity.setName(dto.getName());
		entity.setDetails(dto.getDetails());
		entity.setStatus(dto.getStatus());
		entity.setUrl(dto.getUrl());
		entity.setStack(dto.getStack());
		entity.setTime(dto.getTime());
		return entity;
	}

	/**
	 * Converts Error Entity to DTO.
	 * 
	 * @param entity Must not be null.
	 * 
	 * @return Error DTO
	 */
	public static ErrorDTO convertToDTO(Error entity) {
		ErrorDTO dto = new ErrorDTO();
		dto.setId(entity.getId());
		dto.setErrorId(entity.getErrorId());
		dto.setAppId(entity.getAppId());
		dto.setName(entity.getName());
		dto.setDetails(entity.getDetails());
		dto.setStatus(entity.getStatus());
		dto.setUrl(dto.getUrl());
		dto.setTime(entity.getTime());
		return dto;
	}
}
