package com.adamkorzeniak.masterdata.features.account.service;

import com.adamkorzeniak.masterdata.features.account.model.User;
import com.adamkorzeniak.masterdata.features.account.model.UserDTO;

public class UserServiceHelper {

	private UserServiceHelper() {
	}

	public static User convertToEntity(UserDTO dto) {
		User entity = new User();
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(dto.getPassword());
		entity.setRole(dto.getRole());
		return entity;
	}

	public static UserDTO convertToDTO(User entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
		dto.setRole(entity.getRole());
		// converting from Entity to DTO password should not be populated

		return dto;
	}
}
