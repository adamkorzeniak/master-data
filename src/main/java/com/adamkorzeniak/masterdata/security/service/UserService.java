package com.adamkorzeniak.masterdata.security.service;

import com.adamkorzeniak.masterdata.security.model.User;

public interface UserService {

	User getUser(String username);

	User register(User user);

	String getPrincipal();
}
