package com.smart.simplechat.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.repository.model.UserDAO;

public interface UserRepository extends CrudRepository<UserDAO, Long> {

	/**
	 * 
	 * Checks if the username exists in the DDBB
	 * 
	 * @param userName
	 * @return
	 */
	boolean existsByUserName(String userName);

	/**
	 * 
	 * Retrieves the user identified by its username, if it exists
	 * 
	 * @param userName The user name
	 * @return Optional object containing the user object, if it exists. Otherwise
	 *         an empty Optional
	 */
	Optional<UserDAO> findOptionalByUserName(String userName);

	/**
	 * 
	 * Retrieves the user identified by its username
	 * 
	 * @param userName The user name
	 * @return The user object
	 */
	UserDAO findByUserName(String userName);

}
