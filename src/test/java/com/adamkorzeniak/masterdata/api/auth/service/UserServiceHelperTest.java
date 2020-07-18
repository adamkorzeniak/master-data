package com.adamkorzeniak.masterdata.api.auth.service;

import com.adamkorzeniak.masterdata.api.auth.model.Role;
import com.adamkorzeniak.masterdata.api.auth.model.User;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserRequest;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class UserServiceHelperTest {

    private static final Long ID = 17L;
    private static final String USERNAME = "adam";
    private static final String PASSWORD = "password";
    private static final Role ROLE = Role.ADMIN;

    @Test
    public void ConvertUserDtoToEntity_NoIssuesExpected_ReturnsConvertedEntity() {
        UserRequest request = new UserRequest();
        request.setUsername(USERNAME);
        request.setPassword(PASSWORD);
        request.setRole(ROLE.toString());

        User user = UserServiceHelper.convertFromUserRequest(request);

        assertThat(user).isNotNull();
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo(USERNAME);
        assertThat(user.getPassword()).isEqualTo(PASSWORD);
        assertThat(user.getRole()).isEqualTo(ROLE);
    }

    @Test
    public void ConvertUserEntityToDto_NoIssuesExpected_ReturnsConvertedDto() {
        User entity = new User();
        entity.setId(ID);
        entity.setUsername(USERNAME);
        entity.setPassword(PASSWORD);
        entity.setRole(ROLE);

        UserResponse dto = UserServiceHelper.convertToUserResponse(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(ID);
        assertThat(dto.getUsername()).isEqualTo(USERNAME);
        assertThat(dto.getRole()).isEqualTo(ROLE.toString());
    }
}
