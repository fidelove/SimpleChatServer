package com.smart.simplechat.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smart.simplechat.model.ChatRoom;
import com.smart.simplechat.repository.ChatRoomRepository;
import com.smart.simplechat.repository.model.ChatRoomDAO;
import com.smart.simplechat.repository.model.ChatRoomMessageDAO;
import com.smart.simplechat.util.Mapper;

/**
 * 
 * REST Controller for the resource chat room
 * 
 * @author fidel
 *
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class ChatRoomController {

	@Autowired
	ChatRoomRepository chatRoomRepo;

	/**
	 * API method to return all the existing chat rooms
	 * 
	 * @return A list containing all the existing chat rooms
	 */
	@GetMapping("/rooms")
	public List<ChatRoom> getAllChatRooms() {

		// Get all chat rooms, and convert them
		return StreamSupport.stream(chatRoomRepo.findAll().spliterator(), true).map(Mapper::mapChatRoom)
				.collect(Collectors.toList());
	}

	/**
	 * API method to create a new chat room
	 * 
	 * @param chatRoom Object containing only the chat room name
	 * @return The chat room object created
	 * 
	 * @exception ResponseStatusException with HttpStatus.CONFLICT when the room
	 *                                    name already exists
	 */
	@PostMapping("/rooms")
	public ResponseEntity<Void> createChatRoom(@RequestBody @Valid ChatRoom chatRoom) {

		// If name already exists, throw exception
		if (chatRoomRepo.existsByChatRoomName(chatRoom.getChatRoomName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Room name already exists");
		}

		// Save chatroom and return created instance
		ChatRoomDAO chatRoomDAO = new ChatRoomDAO(null, chatRoom.getChatRoomName(),
				new ArrayList<ChatRoomMessageDAO>());
		// TODO: borrar
//		chatRoomDAO.setChatRoomName(chatRoom.getChatRoomName());
//		chatRoomDAO.setMessages(new ArrayList<ChatRoomMessageDAO>());
		ChatRoomDAO savedChatRoom = chatRoomRepo.save(chatRoomDAO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.header("Location", String.format("/api/v1/room/%d", savedChatRoom.getId())).build();
	}
}
