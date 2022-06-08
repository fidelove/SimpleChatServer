package com.smart.simplechat.repository;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.model.ChatRoomMessage;

public interface ChatRoomMessageRepository extends CrudRepository<ChatRoomMessage, Long> {

}
