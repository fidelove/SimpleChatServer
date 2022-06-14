package com.smart.simplechat;

import static org.junit.Assert.assertEquals;

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
import com.smart.simplechat.model.User;

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest
class UsersControllerTests {

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
	public void createUserOk() throws Exception {

		String uri = "/api/v1/user";
		User newUser = new User();
		newUser.setUserName("user 6");
		newUser.setUserPassword("password");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newUser)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
	}

	@Test
	@Order(2)
	public void createUserDuplicatedUserName() throws Exception {

		String uri = "/api/v1/user";
		User newUser = new User();
		newUser.setUserName("user5");
		newUser.setUserPassword("password");
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				.content(objectMapper.writeValueAsString(newUser)).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		assertEquals(409, mvcResult.getResponse().getStatus());
		assertEquals("User name already exists", mvcResult.getResponse().getErrorMessage());
	}
}