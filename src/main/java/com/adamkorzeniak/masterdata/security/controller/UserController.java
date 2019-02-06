package com.adamkorzeniak.masterdata.security.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adamkorzeniak.masterdata.security.model.User;
import com.adamkorzeniak.masterdata.security.service.UserService;

@RestController
@RequestMapping("/api/v0")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/users")
	public ResponseEntity<Principal> user(Principal user) {
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) {
		user = userService.register(user);
		if (user == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("message", "User already exists");
			return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
}
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<User> loginAsUser(@RequestBody User user) {
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}
