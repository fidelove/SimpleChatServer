package com.smart.simplechat.security.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.smart.simplechat.repository.UserRepository;
import com.smart.simplechat.repository.model.UserDAO;
import com.smart.simplechat.security.model.CurrentUser;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Find the user in DDBB
		Optional<UserDAO> user = userRepo.findOptionalByUserName(username);

		// If ot doesn't exist, exception
		if (user.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}

		// Otherwise, store data
		return new CurrentUser(user.get().getId(), username, user.get().getUserPassword(),
				new ArrayList<GrantedAuthority>());

	}
}