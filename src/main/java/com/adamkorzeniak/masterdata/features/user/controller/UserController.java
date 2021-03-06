package com.adamkorzeniak.masterdata.features.user.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.features.user.model.User;
import com.adamkorzeniak.masterdata.features.user.model.dto.UserRequest;
import com.adamkorzeniak.masterdata.features.user.model.dto.UserResponse;
import com.adamkorzeniak.masterdata.features.user.service.UserService;
import com.adamkorzeniak.masterdata.features.user.service.UserServiceHelper;

@RestController
@RequestMapping(value = "/v0/User")
public class UserController {

    private static final String GET_ME_PATH = "/me";
    private static final String REGISTER_PATH = "/register";

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = GET_ME_PATH)
    public ResponseEntity<UserResponse> user(Principal principal) {
        if (principal == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        User user = userService.getUser(principal);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        UserResponse response = UserServiceHelper.convertToUserResponse(user);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = REGISTER_PATH)
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
        User user = userService.register(UserServiceHelper.convertFromUserRequest(request));
        return new ResponseEntity<>(UserServiceHelper.convertToUserResponse(user), HttpStatus.CREATED);
    }
}
