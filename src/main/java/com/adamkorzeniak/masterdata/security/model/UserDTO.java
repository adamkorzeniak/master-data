package com.adamkorzeniak.masterdata.security.model;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;

	@NotEmpty
	private String username;

	@NotEmpty
	private String password;

	private Role role;

}
