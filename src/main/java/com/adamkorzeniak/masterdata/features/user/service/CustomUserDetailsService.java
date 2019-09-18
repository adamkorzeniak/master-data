package com.adamkorzeniak.masterdata.features.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.adamkorzeniak.masterdata.features.user.model.User;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final String ROLE_PREFIX = "ROLE_";
	private static final String USERNAME_NOT_FOUND_MESSAGE = "Username not found";
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) {
		User user = userService.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException(USERNAME_NOT_FOUND_MESSAGE);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,
				true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole()));
		return authorities;
	}

}
