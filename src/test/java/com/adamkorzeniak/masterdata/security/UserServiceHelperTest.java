package com.adamkorzeniak.masterdata.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.features.account.model.Role;
import com.adamkorzeniak.masterdata.features.account.model.User;
import com.adamkorzeniak.masterdata.features.account.model.UserDTO;
import com.adamkorzeniak.masterdata.features.account.service.UserServiceHelper;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class UserServiceHelperTest {
	
	private static final Long ID = 17L;
	private static final String USERNAME = "adam";
	private static final String PASSWORD = "randomized";
	private static final Role ROLE = Role.ADMIN;

	@Test
	public void ConvertUserDtoToEntity_NoIssuesExpected_ReturnsConvertedEntity() throws Exception {
		UserDTO dto = new UserDTO();
		dto.setId(ID);
		dto.setUsername(USERNAME);
		dto.setPassword(PASSWORD);
		dto.setRole(ROLE);
		
		User user = UserServiceHelper.convertToEntity(dto);
		
		assertThat(user).isNotNull();
		assertThat(user.getId()).isEqualTo(ID);
		assertThat(user.getUsername()).isEqualTo(USERNAME);
		assertThat(user.getPassword()).isEqualTo(PASSWORD);
		assertThat(user.getRole()).isEqualTo(ROLE);
	}

	@Test
	public void ConvertUserEntityToDto_NoIssuesExpected_ReturnsConvertedDto() throws Exception {
		User entity = new User();
		entity.setId(ID);
		entity.setUsername(USERNAME);
		entity.setPassword(PASSWORD);
		entity.setRole(ROLE);
		
		UserDTO dto = UserServiceHelper.convertToDTO(entity);
		
		assertThat(dto).isNotNull();
		assertThat(dto.getId()).isEqualTo(ID);
		assertThat(dto.getUsername()).isEqualTo(USERNAME);
		assertThat(dto.getPassword()).isNull();
		assertThat(dto.getRole()).isEqualTo(ROLE);
	}
}
