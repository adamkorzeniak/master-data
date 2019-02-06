package com.adamkorzeniak.masterdata.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.security.DuplicateUserException;
import com.adamkorzeniak.masterdata.security.model.User;
import com.adamkorzeniak.masterdata.security.repository.UserRepository;

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
		user.setPassword(encoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	@Override
	public String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
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
			throw new DuplicateUserException();
		}
	}

}
