package com.smart.simplechat;

import static org.junit.Assert.assertEquals;

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
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smart.simplechat.model.ChatRoomMessage;
import com.smart.simplechat.model.PrivateMessage;

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
	public void messageSubscribeRoomDoesntExist() throws Exception {
	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(3)
	public void listMessagesFromChatRoomOk() throws Exception {

		String uri = "/api/v1/room/1/messages";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		List<ChatRoomMessage> chatRoomMessages = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				List.class);
		assertEquals("There should be 10 chat messages created", 10, chatRoomMessages.size());
	}

	@Test
	@Order(4)
	public void listMessagesFromChatRoomChatRoomDoesntExist() throws Exception {

		String uri = "/api/v1/room/100/messages";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
		assertEquals("Room doesn't exist", mvcResult.getResponse().getErrorMessage());

	}

	@SuppressWarnings("unchecked")
	@Test
	@Order(5)
	@WithUserDetails("user1")
	public void listMessagesToMeOk() throws Exception {

		String uri = "/api/v1/messages";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
		List<PrivateMessage> chatRoomMessages = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
				List.class);
		assertEquals("There should be 10 chat messages created", 4, chatRoomMessages.size());
	}

	@Test
	@Order(6)
	@WithUserDetails("user1")
	public void postMessageToChatRoomOk() throws Exception {

		String uri = "/api/v1/room/1/messages";
		ChatRoomMessage newMessage = new ChatRoomMessage();
		newMessage.setMessage("new message");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
	}

	@Test
	@Order(7)
	@WithUserDetails("user1")
	public void postMessageToChatRoomChatRoomDoesntExist() throws Exception {

		String uri = "/api/v1/room/100/messages";
		ChatRoomMessage newMessage = new ChatRoomMessage();
		newMessage.setMessage("new message");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
		assertEquals("Room doesn't exist", mvcResult.getResponse().getErrorMessage());
	}

	@Test
	@Order(8)
	@WithUserDetails("user1")
	public void postMessageToUserOk() throws Exception {
		String uri = "/api/v1/user/2/messages";
		ChatRoomMessage newMessage = new ChatRoomMessage();
		newMessage.setMessage("new message");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
	}

	@Test
	@Order(9)
	@WithUserDetails("user1")
	public void postMessageToUserUserDoesntExist() throws Exception {
		String uri = "/api/v1/user/6/messages";
		ChatRoomMessage newMessage = new ChatRoomMessage();
		newMessage.setMessage("new message");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newMessage)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(404, mvcResult.getResponse().getStatus());
		assertEquals("User doesn't exist", mvcResult.getResponse().getErrorMessage());
	}
}