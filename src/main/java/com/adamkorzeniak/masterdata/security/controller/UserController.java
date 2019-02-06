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
import com.adamkorzeniak.masterdata.security.model.UserDTO;
import com.adamkorzeniak.masterdata.security.service.UserService;
import com.adamkorzeniak.masterdata.security.service.UserServiceHelper;

@RestController
@RequestMapping("/api/v0")
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
		if (user == null) {
			HttpHeaders headers = new HttpHeaders();
			headers.add("message", "User already exists");
			return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
}
		return new ResponseEntity<>(UserServiceHelper.convertToDTO(user), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<UserDTO> loginAsUser(@RequestBody UserDTO dto) {
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}

}
