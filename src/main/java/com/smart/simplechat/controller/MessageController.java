package com.smart.simplechat.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.smart.simplechat.model.ChatRoom;
import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.model.PrivateMessage;
import com.smart.simplechat.model.User;
import com.smart.simplechat.repository.ChatRoomMessageRepository;
import com.smart.simplechat.repository.ChatRoomRepository;
import com.smart.simplechat.repository.PrivateMessageRepository;
import com.smart.simplechat.repository.UserRepository;
import com.smart.simplechat.repository.model.ChatRoomDAO;
import com.smart.simplechat.repository.model.ChatRoomMessageDAO;
import com.smart.simplechat.repository.model.PrivateMessageDAO;
import com.smart.simplechat.repository.model.UserDAO;
import com.smart.simplechat.security.model.CurrentUser;
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
public class MessageController {

	@Autowired
	ChatRoomRepository chatRoomRepo;

	@Autowired
	ChatRoomMessageRepository chatRoomMessageRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	PrivateMessageRepository privateMessageRepo;

	/**
	 * 
	 * If the chat room doesn't exists throws an exception If the chat room exists,
	 * it's returned
	 * 
	 * @param roomId The ID
	 * @return The object containing the room object
	 * 
	 * @exception ResponseStatusException with HttpStatus.NOT_FOUND when the room
	 *                                    doesn't exist
	 */
	private ChatRoom getChatRoom(Long roomId) {
		return Mapper.mapChatRoom(getChatRoomDAO(roomId));
	}

	/**
	 * 
	 * If the chat room doesn't exists throws an exception If the chat room exists,
	 * the DAO is returned
	 * 
	 * @param roomId The ID
	 * @return The DAO object containing the room object
	 * 
	 * @exception ResponseStatusException with HttpStatus.NOT_FOUND when the room
	 *                                    doesn't exist
	 */
	private ChatRoomDAO getChatRoomDAO(Long roomId) {
		// If chat room doesn't exist, throw exception
		Optional<ChatRoomDAO> chatRoom = chatRoomRepo.findById(roomId);
		if (chatRoom.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Room doesn't exist");
		}

		return chatRoom.get();
	}

	/**
	 * Return the object containing the info about the session user
	 * 
	 * @return Object containing the information about the user
	 */
	private User getCurrentUser() {
		// Current user is identified, so it must be in DDBB
		return Mapper.mapUser(getCurrentUserDAO());
	}

	/**
	 * Return the DAO object containing the info about the session user
	 * 
	 * @return DAO object containing the information about the user
	 */
	private UserDAO getCurrentUserDAO() {
		// Current user is identified, so it must be in DDBB
		return userRepo
				.findById(((CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId())
				.get();
	}

	/**
	 * 
	 * Gets all the existing messages for a chat room
	 * 
	 * @param roomId ID of the chat room
	 * @return A list containing all the messages of the chat room
	 * 
	 * @exception ResponseStatusException with HttpStatus.NOT_FOUND when the room
	 *                                    doesn't exist
	 */
	@GetMapping("/room/{roomId}/messages")
	public List<ChatRoomMessage> getAllChatRoomMessages(@PathVariable("roomId") @Valid Long roomId) {

		// If the chat room exists, get all existing messages
		ChatRoomDAO chatRoom = getChatRoomDAO(roomId);
		return chatRoomMessageRepo.findByChatRoomId(chatRoom).stream().map(Mapper::mapChatRoomMessage)
				.collect(Collectors.toList());

	}

	/**
	 * Gets all the existing private messages for the current user
	 * 
	 * @return A list containing all the private messages of the current user
	 */
	@GetMapping("/messages")
	public List<PrivateMessage> getAllPrivateMessages() {
		// Gets the private messages from the current user
		return privateMessageRepo.findByDestination(getCurrentUserDAO()).stream().map(Mapper::mapPrivateMessage)
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * Send a message to a chat room
	 * 
	 * @param roomId  ID of the chat room
	 * @param message Message to be sent
	 * @return ResponseEntity with HttpStatus.CREATED if the message has been posted
	 *         successfully
	 * 
	 * @exception ResponseStatusException with HttpStatus.NOT_FOUND when the room
	 *                                    doesn't exist
	 */
	@PostMapping("/room/{roomId}/messages")
	public ResponseEntity<Void> sendMessageToChatRoom(@PathVariable("roomId") @Valid Long roomId,
			@RequestBody @Valid ChatRoomMessage message) {

		// Check if the chat room exist
		ChatRoomDAO existingChatRoom = getChatRoomDAO(roomId);

		// Fill the message information and save it to the DDBB
		ChatRoomMessageDAO chatRoomMessage = new ChatRoomMessageDAO(null, existingChatRoom, getCurrentUserDAO(),
				LocalDateTime.now(), message.getMessage());
		chatRoomMessageRepo.save(chatRoomMessage);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * 
	 * Send a message to a user
	 * 
	 * @param userId  ID of the destination user of the message
	 * @param message The message to be sent
	 * @return ResponseEntity with HttpStatus.CREATED if the message has been posted
	 *         successfully
	 * 
	 * @exception ResponseStatusException with HttpStatus.NOT_FOUND when the user
	 *                                    doesn't exist
	 */
	@PostMapping("/user/{userId}/messages")
	public ResponseEntity<Void> sendMessageToUser(@PathVariable("userId") @Valid Long userId,
			@RequestBody @Valid PrivateMessage message) {

		// Check if the user exists
		Optional<UserDAO> userDestination = userRepo.findById(userId);
		if (userDestination.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exist");
		}

		// Fill the message information and save it to the DDBB
		PrivateMessageDAO privateMessage = new PrivateMessageDAO(null, getCurrentUserDAO(), userDestination.get(),
				LocalDateTime.now(), message.getMessage());
		privateMessageRepo.save(privateMessage);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}