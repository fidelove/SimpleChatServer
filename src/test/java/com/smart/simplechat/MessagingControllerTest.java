package com.smart.simplechat;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.model.PrivateMessage;
import com.smart.simplechat.util.Constants;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Order(3)
public class MessagingControllerTest {

	@Value("${local.server.port}")
	private int port;
	private String URL;

	private CompletableFuture<ChatRoomMessage> completableFutureChatRoom;
	private CompletableFuture<PrivateMessage> completableFuturePrivate;

	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeAll
	public void setup() throws Exception {
		URL = "ws://localhost:" + port + Constants.WEBSOCKET_ENDPOINT;
		completableFutureChatRoom = new CompletableFuture<ChatRoomMessage>();
		completableFuturePrivate = new CompletableFuture<PrivateMessage>();
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);
	}

	@Test
	@WithUserDetails("user1")
	@Order(1)
	public void testSendChatRoomMessage() throws Exception {

		// Create the websocket
		WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		// User credentials
		String plainCredentials = "user1:password";
		String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
		final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);

		// Starts the connection
		StompSession stompSession = stompClient.connect(URL, headers, new StompSessionHandlerAdapter() {
		}).get(1, SECONDS);

		// And subscribe to get responses of chat room 1 through the websocket
		stompSession.subscribe("/queue/rooms/1/subscribe", new CreateChatRoomMessageStompFrameHandler());

		// Invokes a REST resource
		String uri = "/api/v1/room/1/messages";

		// Create the new message
		ChatRoomMessage newMessage = new ChatRoomMessage();
		newMessage.setMessage("new message");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		// The REST call was correct
		assertEquals(201, mvcResult.getResponse().getStatus());

		// And the message was received through the websocket
		ChatRoomMessage chatState = completableFutureChatRoom.get(1, SECONDS);

		assertNotNull(chatState);
		assertEquals("The message received is the same to the sent one", newMessage.getMessage(),
				chatState.getMessage());

		stompSession.disconnect();
	}

	@Test
	@WithUserDetails("user2")
	@Order(2)
	public void testSendPrivateMessage() throws Exception {

		// Create the websocket
		WebSocketStompClient stompClient = new WebSocketStompClient(new SockJsClient(createTransportClient()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		// User credentials
		String plainCredentials = "user1:password";
		String base64Credentials = Base64.getEncoder().encodeToString(plainCredentials.getBytes());
		final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
		headers.add("Authorization", "Basic " + base64Credentials);

		// Starts the connection
		StompSession stompSession = stompClient.connect(URL, headers, new StompSessionHandlerAdapter() {
		}).get(100, SECONDS);

		// And subscribe to get responses of chat room 1 through the websocket
		stompSession.subscribe("/user/queue/private-message", new CreatePrivateMessageStompFrameHandler());

		// Invokes a REST resource
		String uri = "/api/v1/user/1/messages";
		PrivateMessage newMessage = new PrivateMessage();
		newMessage.setMessage("new private message from user 2 to user 1");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		// The REST call was correct
		assertEquals(201, mvcResult.getResponse().getStatus());

		// And the message was received through the websocket
		PrivateMessage chatState = completableFuturePrivate.get(1, SECONDS);

		assertNotNull(chatState);
		assertEquals("The message received is the same to the sent one", newMessage.getMessage(),
				chatState.getMessage());

		stompSession.disconnect();

	}

	private List<Transport> createTransportClient() {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	private class CreateChatRoomMessageStompFrameHandler implements StompFrameHandler {
		@Override
		public Type getPayloadType(StompHeaders stompHeaders) {
			return ChatRoomMessage.class;
		}

		@Override
		public void handleFrame(StompHeaders stompHeaders, Object o) {
			completableFutureChatRoom.complete((ChatRoomMessage) o);
		}
	}

	private class CreatePrivateMessageStompFrameHandler implements StompFrameHandler {
		@Override
		public Type getPayloadType(StompHeaders stompHeaders) {
			return PrivateMessage.class;
		}

		@Override
		public void handleFrame(StompHeaders stompHeaders, Object o) {
			completableFuturePrivate.complete((PrivateMessage) o);
		}
	}
}