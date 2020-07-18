package com.adamkorzeniak.masterdata.api.auth.service;

import com.adamkorzeniak.masterdata.api.auth.model.Role;
import com.adamkorzeniak.masterdata.api.auth.model.User;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserRequest;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserResponse;

public class UserServiceHelper {

    private UserServiceHelper() {
    }

    public static UserResponse convertToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().toString());
        return response;
    }

    public static User convertFromUserRequest(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(Role.valueOf(request.getRole().toUpperCase()));
        return user;
    }
}
