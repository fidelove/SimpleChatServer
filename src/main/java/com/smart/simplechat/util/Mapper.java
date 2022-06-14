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
 * Class in charge of mapping DAOs into models
 * 
 * @author fidel
 *
 */
public class Mapper {

	/**
	 * Maps a ChatRoomDAO into a ChatRoom
	 * 
	 * @param chatRoom The ChatRoomDAO
	 * @return The mapped instance of the ChatRoom
	 */
	public static ChatRoom mapChatRoom(ChatRoomDAO chatRoom) {
		return new ChatRoom(chatRoom.getId(), chatRoom.getChatRoomName());
	}

	/**
	 * Maps a ChatRoomMessageDAO into a ChatRoomMessage
	 * 
	 * @param chatRoomMessage The ChatRoomMessageDAO
	 * @return The mapped instance of the ChatRoomMessage
	 */
	public static ChatRoomMessage mapChatRoomMessage(ChatRoomMessageDAO chatRoomMessage) {
		return new ChatRoomMessage(chatRoomMessage.getCreator().getUserName(), chatRoomMessage.getCreated(),
				chatRoomMessage.getMessage());
	}

	/**
	 * 
	 * Maps a PrivateMessageDAO into a PrivateMessage
	 * 
	 * @param privateMessage The PrivateMessageDAO
	 * @return The mapped instance of the PrivateMessage
	 */
	public static PrivateMessage mapPrivateMessage(PrivateMessageDAO privateMessage) {
		return new PrivateMessage(privateMessage.getCreator().getUserName(),
				privateMessage.getDestination().getUserName(), privateMessage.getCreated(),
				privateMessage.getMessage());
	}

	/**
	 * 
	 * Maps a UserDAO into a User
	 * 
	 * @param user The UserDAO
	 * @return The mapped instance of the User
	 */
	public static User mapUser(UserDAO user) {
		return new User(user.getId(), user.getUserName(), user.getUserPassword(),
				user.getPrivateMessages().stream().map(Mapper::mapPrivateMessage).collect(Collectors.toList()));
	}
}