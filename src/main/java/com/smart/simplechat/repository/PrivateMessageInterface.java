package com.smart.simplechat.repository;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.model.PrivateMessage;

public interface PrivateMessageInterface extends CrudRepository<PrivateMessage, Long> {

}
