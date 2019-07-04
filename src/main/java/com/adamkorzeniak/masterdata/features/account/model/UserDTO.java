package com.adamkorzeniak.masterdata.features.account.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

	private Long id;

	@NotEmpty
	@NotNull
	private String username;

	@NotEmpty
	private String password;

	@NotEmpty
	private Role role;

}
