package com.smart.simplechat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.repository.model.PrivateMessageDAO;
import com.smart.simplechat.repository.model.UserDAO;

public interface PrivateMessageRepository extends CrudRepository<PrivateMessageDAO, Long> {

	List<PrivateMessageDAO> findByDestination(UserDAO destination);
}