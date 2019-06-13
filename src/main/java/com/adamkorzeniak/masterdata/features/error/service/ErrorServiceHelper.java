package com.adamkorzeniak.masterdata.features.error.service;

import com.adamkorzeniak.masterdata.features.error.model.Error;
import com.adamkorzeniak.masterdata.features.error.model.ErrorDTO;

public class ErrorServiceHelper {

	private ErrorServiceHelper() {}

	/**
	 * 
	 * Converts Error DTO to Entity.
	 * 
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
	 * 
	 * Converts Error Entity to DTO.
	 * 
	 */
	public static ErrorDTO convertToDTO(Error entity) {
		ErrorDTO dto = new ErrorDTO();
		dto.setId(entity.getId());
		dto.setErrorId(entity.getErrorId());
		dto.setAppId(entity.getAppId());
		dto.setName(entity.getName());
		dto.setDetails(entity.getDetails());
		dto.setStatus(entity.getStatus());
		dto.setUrl(entity.getUrl());
		dto.setStack(entity.getStack());
		dto.setTime(entity.getTime());
		return dto;
	}
}
