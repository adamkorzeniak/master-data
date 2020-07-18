package com.adamkorzeniak.masterdata.api.auth.controller;

import com.adamkorzeniak.masterdata.api.auth.model.User;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserRequest;
import com.adamkorzeniak.masterdata.api.auth.model.dto.UserResponse;
import com.adamkorzeniak.masterdata.api.auth.service.UserService;
import com.adamkorzeniak.masterdata.api.auth.service.UserServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/User")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
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

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody @Valid UserRequest request) {
        User user = userService.register(UserServiceHelper.convertFromUserRequest(request));
        return new ResponseEntity<>(UserServiceHelper.convertToUserResponse(user), HttpStatus.CREATED);
    }
}