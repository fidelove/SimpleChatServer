package com.smart.simplechat.messaging.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.model.PrivateMessage;
import com.smart.simplechat.util.Constants;

@Controller
@CrossOrigin
public class MessagingController {

	@Autowired
	private SimpMessagingTemplate simpMessaging;

	/**
	 * Send a message through a websocket to the subscribers of a chat room
	 * 
	 * @param roomId  The ID of the chat room
	 * @param message The message to be sent
	 */
	public void sendChatRoomMessage(@DestinationVariable Long roomId, ChatRoomMessage message) {
		simpMessaging.convertAndSend(String.format(Constants.WEBSOCKET_CHAT_ROOM_SUBSCRIPTION, roomId), message);
	}

	/**
	 * Sends a message through the websocket to a single user
	 * 
	 * @param message The message to be sent
	 */

	public void sendPrivateMessage(PrivateMessage message) {
		simpMessaging.convertAndSendToUser(message.getDestination(), Constants.WEBSOCKET_PRIVATE_DEST_PREFIX, message);
	}
}