package com.adamkorzeniak.masterdata.features.user.service;

import java.security.Principal;

import com.adamkorzeniak.masterdata.features.user.model.User;

public interface UserService {

	User getUser(String username);
	
	User getUser(Principal user);

	User register(User user);
}
