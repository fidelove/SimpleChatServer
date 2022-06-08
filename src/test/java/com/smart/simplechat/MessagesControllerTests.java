package com.smart.simplechat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class MessagesControllerTests {

	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@Autowired
	WebApplicationContext webApplicationContext;

	@BeforeAll
	public void setup() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_EMPTY);

	}

	@Test
	@Order(1)
	public void messageSubscribeOk() throws Exception {
	}

	@Test
	@Order(2)
	public void messageSubscribeUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(3)
	public void messageSubscribeRoomDoesntExist() throws Exception {
	}

	@Test
	@Order(4)
	public void listMessagesFromChatRoomOk() throws Exception {
	}

	@Test
	@Order(5)
	public void listMessagesFromChatRoomUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(6)
	public void listMessagesFromChatRoomChatRoomDoesntExist() throws Exception {
	}

	@Test
	@Order(7)
	public void listMessagesToMeOk() throws Exception {
	}

	@Test
	@Order(8)
	public void listMessagesToMeUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(9)
	public void listMessagesToMeIDontExist() throws Exception {
	}

	@Test
	@Order(10)
	public void postMessageToChatRoomOk() throws Exception {
	}

	@Test
	@Order(11)
	public void postMessageToChatRoomUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(12)
	public void postMessageToChatRoomChatRoomDoesntExist() throws Exception {
	}

	@Test
	@Order(13)
	public void postMessageToUserOk() throws Exception {
	}

	@Test
	@Order(14)
	public void postMessageToUserUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(15)
	public void postMessageToUserUserDoesntExist() throws Exception {
	}

}
