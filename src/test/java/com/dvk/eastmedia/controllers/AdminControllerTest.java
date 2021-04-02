package com.dvk.eastmedia.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dvk.eastmedia.entities.Article;
import com.dvk.eastmedia.repos.ArticleRepo;
import com.dvk.eastmedia.repos.CommentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

	@Autowired
	MockMvc mock;
	
	@MockBean
	ArticleRepo articleRepo;
	
	@MockBean
	CommentRepo commentRepo;
	
	@Test
	public void addArticleTest() throws Exception {
		mock.perform(MockMvcRequestBuilders.post("/articles")
						.accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.content(new ObjectMapper().writeValueAsString(new Article(0, "title", "content", null, 0, 0, null, null))))
				.andExpect(status().isCreated())
				.andExpect(header().exists("location"))
				.andReturn();
	}

}
