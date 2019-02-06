package com.adamkorzeniak.masterdata.security.service;

import com.adamkorzeniak.masterdata.security.model.User;
import com.adamkorzeniak.masterdata.security.model.UserDTO;

public class UserServiceHelper {
	
	private UserServiceHelper() {}
	
	public static User convertToEntity(UserDTO dto) {
		User entity = new User();
		entity.setId(dto.getId());
		entity.setUsername(dto.getUsername());
		entity.setPassword(entity.getPassword());
		entity.setRole(dto.getRole());
		
		return entity;
	}
	
	public static UserDTO convertToDTO(User entity) {
		UserDTO dto = new UserDTO();
		dto.setId(entity.getId());
		dto.setUsername(entity.getUsername());
		dto.setRole(entity.getRole());
		//converting from Entity to DTO password should not be populated
		
		return dto;
	}
}
