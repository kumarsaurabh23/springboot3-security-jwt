package com.example.security.jwt;

import com.example.security.jwt.dto.AuthenticationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class Springboot3SecurityJwtApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	private String token;

	@BeforeEach
	public void testToken() throws Exception {
		AuthenticationDto dto = new AuthenticationDto();
		dto.setUsername("user1");
		dto.setPassword("pwd1");
		String body = new ObjectMapper().writeValueAsString(dto);
		token = mockMvc.perform(MockMvcRequestBuilders.post("/security/authenticate")
						.contentType(MediaType.APPLICATION_JSON)
						.content(body))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
	}

	@Test
	void testNormal() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/security/normal")
					.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(status().isForbidden());
	}

	@Test
	void testAdmin() throws Exception {
		String result = mockMvc.perform(MockMvcRequestBuilders.get("/security/admin")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		Assertions.assertEquals("Welcome Admin User!", result);
	}

}
