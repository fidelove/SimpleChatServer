package com.smart.simplechat.repository;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.repository.model.ChatRoomDAO;

public interface ChatRoomRepository extends CrudRepository<ChatRoomDAO, Long> {

	boolean existsByChatRoomName(String chatRoomName);

}
