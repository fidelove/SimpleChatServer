package com.smart.simplechat.messaging.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.smart.simplechat.util.Constants;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint(Constants.WEBSOCKET_ENDPOINT).setAllowedOriginPatterns("*").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		// 2 message brokers, the first one for private messages, and the second one for
		// chat rooms
		config.enableSimpleBroker(Constants.WEBSOCKET_PRIVATE_DEST_PREFIX, Constants.WEBSOCKET_CHAT_ROOM_DEST_PREFIX);
	}
}