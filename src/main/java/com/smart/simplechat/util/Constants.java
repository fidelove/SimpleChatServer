package com.smart.simplechat.util;

public interface Constants {

	public static final String LOCATION_HEADER = "Location";

	public static final String WEBSOCKET_ENDPOINT = "/rooms";
	public static final String WEBSOCKET_CHAT_ROOM_DEST_PREFIX = "/queue";
	public static final String WEBSOCKET_CHAT_ROOM_SUBSCRIPTION = "/queue/rooms/%d/subscribe";
	public static final String WEBSOCKET_PRIVATE_DEST_PREFIX = "/queue/private-message";

}