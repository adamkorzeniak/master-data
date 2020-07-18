package com.adamkorzeniak.masterdata.api.auth.service;

import com.adamkorzeniak.masterdata.api.auth.model.User;

import java.security.Principal;

public interface UserService {

    User getUser(String username);

    User getUser(Principal user);

    User register(User user);
}
