package com.smart.simplechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smart.simplechat.model.ChatRoom;
import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.repository.ChatRoomRepository;

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
		return Lists.newArrayList(chatRoomRepo.findAll());
	}

	/**
	 * API method to create a new chat room
	 * 
	 * @param chatRoom Object containing only the chat room name
	 * @return The chat room object created
	 * 
	 * @exception ResponseStatusException HTTP response 409 when the room name
	 *                                    already exists
	 */
	@PostMapping("/rooms")
	public ResponseEntity<ChatRoom> createChatRoom(@RequestBody @Valid ChatRoom chatRoom) {

		// If name already exists, throw exception
		if (chatRoomRepo.existsByChatRoomName(chatRoom.getChatRoomName())) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Room name already exists");
		}

		// Save chatroom and return created instance
		chatRoom.setMessages(new ArrayList<ChatRoomMessage>());
		ChatRoom savedChatRoom = chatRoomRepo.save(chatRoom);
		return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON)
				.header("Location", String.format("/api/v1/room/%d", savedChatRoom.getId())).body(savedChatRoom);
	}
}
