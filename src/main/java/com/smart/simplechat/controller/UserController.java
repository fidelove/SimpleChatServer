package com.smart.simplechat.controller;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smart.simplechat.model.User;
import com.smart.simplechat.repository.UserRepository;
import com.smart.simplechat.repository.model.PrivateMessageDAO;
import com.smart.simplechat.repository.model.UserDAO;

/**
 * 
 * REST Controller for the user chat room
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
	 * @return An object containing aditionally the user ID
	 * 
	 * @exception
	 */
	@PostMapping("/user")
	public ResponseEntity<Void> createUser(@RequestBody @Valid User user) {

		// If user name already exists throw exception
		if (userRepo.existsByUserName(user.getUserName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "User name already exists");
		}

		// Otherwise, store and return an active user
		UserDAO userDao = new UserDAO(user.getUserName(),
				PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getUserPassword()), true,
				new ArrayList<PrivateMessageDAO>());
		userRepo.save(userDao);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/login")
	public ResponseEntity<Void> login() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}
}