package com.adamkorzeniak.masterdata.features.user.service;

import com.adamkorzeniak.masterdata.features.user.model.User;

import java.security.Principal;

public interface UserService {

    User getUser(String username);

    User getUser(Principal user);

    User register(User user);
}
