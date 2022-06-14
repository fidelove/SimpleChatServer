package com.smart.simplechat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.simplechat.model.ChatRoom;
import com.smart.simplechat.util.Constants;

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

	@SuppressWarnings("unchecked")
	@Test
	@Order(1)
	public void listExistingChatRooms() throws Exception {

		String uri = "/api/v1/rooms";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		List<ChatRoom> allChatRooms = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), List.class);
		assertEquals("There should be 10 chat rooms created", 10, allChatRooms.size());
	}

	@Test
	@Order(2)
	public void createChatRoomOk() throws Exception {

		String uri = "/api/v1/rooms";
		ChatRoom newChatRoom = new ChatRoom();
		newChatRoom.setChatRoomName("chatroom 11");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newChatRoom)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
		String locationHeader = mvcResult.getResponse().getHeader(Constants.LOCATION_HEADER);
		assertTrue("There should be the Location header containing the path to the created resource",
				locationHeader.contains("/api/v1/room/11"));
	}

	@Test
	@Order(2)
	public void createChatRoomNameBadInput() throws Exception {

		String uri = "/api/v1/rooms";
		ChatRoom newChatRoom = new ChatRoom();
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newChatRoom)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(400, mvcResult.getResponse().getStatus());
	}

	@Test
	@Order(4)
	public void createChatRoomNameNameAlreadyExists() throws Exception {

		String uri = "/api/v1/rooms";
		ChatRoom newChatRoom = new ChatRoom();
		newChatRoom.setChatRoomName("chatroom 10");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newChatRoom)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(409, mvcResult.getResponse().getStatus());
		assertEquals("Room name already exists", mvcResult.getResponse().getErrorMessage());
	}
}