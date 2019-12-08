package com.adamkorzeniak.masterdata.features.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.exception.exceptions.DuplicateUserException;
import com.adamkorzeniak.masterdata.features.user.model.Role;
import com.adamkorzeniak.masterdata.features.user.model.User;
import com.adamkorzeniak.masterdata.features.user.repository.UserRepository;

import java.security.Principal;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class UserServiceTest {

    private static final Long ID = 123L;
    private static final String USERNAME = "adam";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "$10$encodedpassword";
    private static final Role ROLE = Role.USER;

    private static final String DUPLICATED_USER_MESSAGE =
            String.format("User with username '%s' already exists", USERNAME);

    @MockBean
    private PasswordEncoder encoder;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    public void RegisterUser_NoIssues_ReturnsUser() {

        User adam = new User();
        adam.setUsername(USERNAME);
        adam.setPassword(PASSWORD);
        adam.setRole(ROLE);

        User mockedUser = new User();
        mockedUser.setId(ID);
        mockedUser.setUsername(USERNAME);
        mockedUser.setPassword(ENCODED_PASSWORD);
        mockedUser.setRole(ROLE);

        when(encoder.encode(PASSWORD)).thenReturn(ENCODED_PASSWORD);
        when(userRepository.findByUsername(USERNAME)).thenReturn(null);
        when(userRepository.save(ArgumentMatchers.any())).thenReturn(mockedUser);

        User result = userService.register(adam);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID.intValue());
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getPassword()).isEqualTo(ENCODED_PASSWORD);
        assertThat(result.getRole()).isEqualTo(ROLE);
    }

    @Test
    public void RegisterUser_UserAlreadyExists_ThrowsException() {
        User adam = new User();
        adam.setUsername(USERNAME);
        adam.setPassword(PASSWORD);
        adam.setRole(ROLE);

        when(userRepository.findByUsername(USERNAME)).thenReturn(adam);

        assertThatExceptionOfType(DuplicateUserException.class).isThrownBy(() -> userService.register(adam)).withMessage(DUPLICATED_USER_MESSAGE);
    }

    @Test
    public void RegisterUser_NoUser_ThrowsException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> userService.register(null)).withMessage(null);
    }

    @Test
    public void RetrieveUser_UserExists_ReturnsUser() {
        Principal principal = Mockito.mock(Principal.class);
        User adam = new User();
        adam.setId(ID);
        adam.setUsername(USERNAME);
        adam.setPassword(PASSWORD);
        adam.setRole(ROLE);

        when(principal.getName()).thenReturn(USERNAME);
        when(userRepository.findByUsername(USERNAME)).thenReturn(adam);

        User result = userService.getUser(principal);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(ID);
        assertThat(result.getUsername()).isEqualTo(USERNAME);
        assertThat(result.getPassword()).isEqualTo(PASSWORD);
        assertThat(result.getRole()).isEqualTo(ROLE);
    }

    @Test
    public void RetrieveUser_UserNotExists_ThrowsException() {
        String username = null;
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> userService.getUser(username)).withMessage(null);
    }
}
