package com.adamkorzeniak.masterdata.features.account.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.features.account.model.User;
import com.adamkorzeniak.masterdata.features.account.model.UserDTO;
import com.adamkorzeniak.masterdata.features.account.service.UserService;
import com.adamkorzeniak.masterdata.features.account.service.UserServiceHelper;

@RestController
@RequestMapping("/v0")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/me")
	public ResponseEntity<Principal> user(Principal user) {
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO dto) {
		User user = userService.register(UserServiceHelper.convertToEntity(dto));
		return new ResponseEntity<>(UserServiceHelper.convertToDTO(user), HttpStatus.CREATED);
	}
}
