package com.smart.simplechat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.smart.simplechat.repository.model.ChatRoomDAO;
import com.smart.simplechat.repository.model.ChatRoomMessageDAO;

public interface ChatRoomMessageRepository extends CrudRepository<ChatRoomMessageDAO, Long> {

	public List<ChatRoomMessageDAO> findByChatRoomId(ChatRoomDAO chatRoomId);

}
