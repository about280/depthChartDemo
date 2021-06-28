package com.about280.ashleigh.sports.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
public class DepthChartControllerIntegrationTest {
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mvc;

	@BeforeEach
	public void setUp(RestDocumentationContextProvider restDocumentation) {
		this.mvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(documentationConfiguration(restDocumentation)).build();
	}
	
	@Test
	void testErrorFromClient() throws Exception {
		mvc.perform(get("/sports")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().is4xxClientError());
	}
	
	@Test
	void testGetDepthBySportId() throws Exception {
		mvc.perform(get("/sports/Test")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isNotFound());
	}
	
	@Test
	void testAllAddressBookEntries() throws Exception {
		mvc.perform(get("/sports/NFL")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isOk());
	}
	
	
	@Test
	void testAddAddressBooksEntry() throws Exception {
		mvc.perform(put("/players/TEST")
			      .contentType(MediaType.APPLICATION_JSON))
			      .andExpect(status().isCreated());
	}

}
