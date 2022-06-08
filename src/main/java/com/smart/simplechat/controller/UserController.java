package com.smart.simplechat.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smart.simplechat.model.User;
import com.smart.simplechat.repository.UserRepository;

/**
 * 
 * REST Controller for the user chat room
 * 
 * @author fidel
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class UserController {

	@Autowired
	UserRepository userRepo;

	/**
	 * 
	 * API method to create a new user
	 * 
	 * @param user The object containing a username and a user password
	 * @return An dobject containing aditionally the user ID
	 */
	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody @Valid User user) {

		// If user name already exists throw exception
		if (userRepo.existsByUserName(user.getUserName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User name already exists");
		}

		// Otherwise, store and return an active user
		user.setIsActive(true);
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.body(userRepo.save(user));
	}
}