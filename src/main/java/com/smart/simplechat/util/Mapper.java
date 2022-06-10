package com.smart.simplechat.util;

import java.util.stream.Collectors;

import com.smart.simplechat.model.ChatRoom;
import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.model.PrivateMessage;
import com.smart.simplechat.model.User;
import com.smart.simplechat.repository.model.ChatRoomDAO;
import com.smart.simplechat.repository.model.ChatRoomMessageDAO;
import com.smart.simplechat.repository.model.PrivateMessageDAO;
import com.smart.simplechat.repository.model.UserDAO;

/**
 * 
 * Class in charge of map DAOs into model
 * 
 * @author fidel
 *
 */
public class Mapper {

	/**
	 * Maps a ChatRoomDAO into a ChatRoom
	 * 
	 * @param chatRoom The ChatRoomDAO
	 * @return The ChatRoom
	 */
	public static ChatRoom mapChatRoom(ChatRoomDAO chatRoom) {
		return new ChatRoom(chatRoom.getId(), chatRoom.getChatRoomName(),
				chatRoom.getMessages().stream().map(Mapper::mapChatRoomMessage).collect(Collectors.toList()));
	}

	/**
	 * Maps a ChatRoomMessageDAO into a ChatRoomMessage
	 * 
	 * @param chatRoomMessage The ChatRoomMessageDAO
	 * @return The ChatRoomMessage
	 */
	public static ChatRoomMessage mapChatRoomMessage(ChatRoomMessageDAO chatRoomMessage) {
		return new ChatRoomMessage(chatRoomMessage.getId(), chatRoomMessage.getChatRoomId().getId(),
				chatRoomMessage.getCreator().getId(), chatRoomMessage.getCreated(), chatRoomMessage.getMessage());
	}

	/**
	 * 
	 * 
	 * 
	 * @param privateMessage
	 * @return
	 */
	public static PrivateMessage mapPrivateMessage(PrivateMessageDAO privateMessage) {
		return new PrivateMessage(privateMessage.getId(), privateMessage.getCreator().getId(),
				privateMessage.getDestination().getId(), privateMessage.getCreated(), privateMessage.getMessage());
	}

	public static User mapUser(UserDAO user) {
		return new User(user.getId(), user.getUserName(), user.getUserPassword(),
				user.getPrivateMessages().stream().map(Mapper::mapPrivateMessage).collect(Collectors.toList()));
	}
}