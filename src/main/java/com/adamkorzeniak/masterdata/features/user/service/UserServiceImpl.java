package com.adamkorzeniak.masterdata.features.user.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.exception.exceptions.DuplicateUserException;
import com.adamkorzeniak.masterdata.features.user.model.Role;
import com.adamkorzeniak.masterdata.features.user.model.User;
import com.adamkorzeniak.masterdata.features.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;
	
	@Override
	public User register(User user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		ifUserNotExistsAlready(user.getUsername());

		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(encoder.encode(user.getPassword()));
		newUser.setRole(user.getRole());
		if (newUser.getRole() == null) {
			newUser.setRole(Role.USER);
		}

		return userRepository.save(newUser);
	}

	@Override
	public User getUser(String username) {
		if (username == null) {
			throw new IllegalArgumentException();
		}
		return userRepository.findByUsername(username);
	}

	private void ifUserNotExistsAlready(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			throw new DuplicateUserException(username);
		}
	}

	@Override
	public User getUser(Principal user) {
		return getUser(user.getName());
	}

}
