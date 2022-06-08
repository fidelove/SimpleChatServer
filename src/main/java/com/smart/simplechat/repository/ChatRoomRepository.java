package com.smart.simplechat.repository;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.model.ChatRoom;

public interface ChatRoomRepository extends CrudRepository<ChatRoom, Long> {

}
