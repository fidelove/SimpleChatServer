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
class ChatRoomsControllerTests {

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
	public void listExistingChatRooms() throws Exception {
	}

	@Test
	@Order(2)
	public void listExistingChatRoomsUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(3)
	public void createChatRoomOk() throws Exception {
	}

	@Test
	@Order(4)
	public void createChatRoomNameBadInput() throws Exception {
	}

	@Test
	@Order(5)
	public void createChatRoomNameUserNotAuthenticated() throws Exception {
	}

	@Test
	@Order(6)
	public void createChatRoomNameNameAlreadyExists() throws Exception {
	}

}
