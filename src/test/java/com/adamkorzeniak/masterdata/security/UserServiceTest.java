package com.adamkorzeniak.masterdata.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.mockito.ArgumentMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.adamkorzeniak.masterdata.exception.exceptions.DuplicateUserException;
import com.adamkorzeniak.masterdata.features.account.model.User;
import com.adamkorzeniak.masterdata.features.account.repository.UserRepository;
import com.adamkorzeniak.masterdata.features.account.service.UserService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = "test")
@SpringBootTest
public class UserServiceTest {

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	public void RegisterUser_NoIssues_ReturnsUser() throws Exception {
		String username = "adam";
		User adam = new User();
		adam.setId(11L);
		adam.setUsername(username);
		adam.setPassword("password");

		when(encoder.encode("password")).thenReturn("$10$encodedpassword");
		when(userRepository.findByUsername(username)).thenReturn(null);
		when(userRepository.save(ArgumentMatchers.<User>any())).thenAnswer(i -> i.getArguments()[0]);

		User result = userService.register(adam);

		assertThat(result).isNotNull();
		assertThat(result.getUsername()).isEqualTo(username);
		assertThat(result.getPassword()).isEqualTo("$10$encodedpassword");
	}

	@Test
	public void RegisterUser_UserAlreadyExists_ThrowsException() throws Exception {
		String username = "adam";
		User adam = new User();
		adam.setId(11L);
		adam.setUsername(username);
		adam.setPassword("password");

		when(userRepository.findByUsername(username)).thenReturn(adam);

		assertThatExceptionOfType(DuplicateUserException.class).isThrownBy(() -> {
			userService.register(adam);
		}).withMessage("User with username 'adam' already exists");
	}

	@Test
	public void RegisterUser_NoUser_ThrowsException() throws Exception {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			userService.register(null);
		}).withMessage(null);
	}

	@Test
	public void RetrieveUser_UserExists_ReturnsUser() throws Exception {
		String username = "adam";
		User adam = new User();
		adam.setId(11L);
		adam.setUsername(username);
		adam.setPassword("password");

		when(userRepository.findByUsername(username)).thenReturn(adam);

		User result = userService.getUser(username);

		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(11L);
		assertThat(result.getUsername()).isEqualTo(username);
		assertThat(result.getPassword()).isEqualTo("password");
	}

	@Test
	public void RetrieveUser_UserNotExists_ThrowsException() throws Exception {
		assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
			userService.getUser(null);
		}).withMessage(null);
	}
}
