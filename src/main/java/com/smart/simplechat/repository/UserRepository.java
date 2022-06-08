package com.smart.simplechat.repository;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	boolean existsByUserName(String userName);

}
