package com.smart.simplechat.security.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * CurrentUser class that extends User, in order to save in the security context
 * the user ID
 * 
 * @author fidel
 *
 */
@Getter
@Setter
@ToString
public class CurrentUser extends User {

	private static final long serialVersionUID = 9119872561893152939L;
	private Long id;

	/**
	 * Contructor with parameters
	 * 
	 * @param id
	 * @param username
	 * @param password
	 * @param authorities
	 */
	public CurrentUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}
}