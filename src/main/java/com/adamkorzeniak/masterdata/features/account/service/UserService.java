package com.adamkorzeniak.masterdata.features.account.service;

import com.adamkorzeniak.masterdata.features.account.model.User;

public interface UserService {

	User getUser(String username);

	User register(User user);

	String getPrincipal();
}
