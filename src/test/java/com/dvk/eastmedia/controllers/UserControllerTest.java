package com.dvk.eastmedia.controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.dvk.eastmedia.entities.Article;
import com.dvk.eastmedia.entities.Comment;
import com.dvk.eastmedia.repos.ArticleRepo;
import com.dvk.eastmedia.repos.CommentRepo;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;

@WebMvcTest(value=UserController.class)
class UserControllerTest {
	
	@MockBean
	ArticleRepo articleRepo;
	
	@MockBean
	CommentRepo commentRepo;
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	public void testGetArticlesWithEmptyData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/articles").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("[]"))
					.andReturn();					
	}

	
	@Test
	public void testGetArticles() throws Exception {
		List<Article> articlesList = Arrays.asList(new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null));
		when(articleRepo.findAll()).thenReturn(articlesList);
		mockMvc.perform(MockMvcRequestBuilders.get("/articles").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("[{title:title,content:content}]"))
					.andReturn();					
	}
	
	@Test
	public void testGetArticleWithEmptyData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/0").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andReturn();						
	}
	
	@Test
	public void testGetArticle() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null);
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/0").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("{title:title,content:content}"))
					.andReturn();			
	}
	
	@Test
	public void testGetCommentsWithWrongArticleId() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/0/comments").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isNotFound())
					.andReturn();
	}
	
	@Test
	public void testGetCommentsWithNoData() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, new HashSet<Comment>(), null);
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/0/comments").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("[]"))
					.andReturn();
	}
	
	@Test
	public void testGetComments() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null);
		article.setComments(new HashSet<Comment>(Arrays.asList(new Comment(0, "vinay", "someMessage", LocalDateTime.now(), 0,null))));
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.get("/articles/0/comments").accept(MediaType.APPLICATION_JSON))
					.andExpect(status().isOk())
					.andExpect(content().json("[{id:0,commentedBy:vinay,message:someMessage,likes:0 }]"))
					.andReturn();
	}
	
	@Test
	public void testAddComment() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null);
		Comment comment =  new Comment(0, "vinay", "someMessage", LocalDateTime.now(), 0, article);
		when(commentRepo.save(comment)).thenReturn(comment);
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/comments")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(new ObjectMapper().writeValueAsString(new Comment(0, "vinay", "someMessage", null, 0, null))))
								.andExpect(status().isCreated())
								.andExpect(header().exists("location"))
								.andReturn();
	}
	
	@Test
	public void testAddCommentWithoutData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/comments")
							.contentType(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.content(new ObjectMapper().writeValueAsString(new Comment(0, "vinay", "someMessage", null, 0, null))))
								.andExpect(status().isNotFound())
								.andReturn();
	}
	
	@Test
	public void testLike() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null);
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/like")
							.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk())
								.andExpect(content().json("{id:0,title:title,content:content,likes:1,views:0}"))
								.andReturn();
	}
	
	@Test
	public void testLikeWithoutData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/like")
							.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isNotFound())
								.andReturn();
	}
	
	@Test
	public void testView() throws Exception {
		Article article = new Article(0, "title", "content", LocalDateTime.now(), 0, 0, null, null);
		when(articleRepo.findById(0)).thenReturn(Optional.of(article));
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/view")
							.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk())
								.andExpect(content().json("{id:0,title:title,content:content,likes:0,views:1}"))
								.andReturn();
	}
	
	@Test
	public void testViewWithoutData() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.post("/articles/0/view")
							.accept(MediaType.APPLICATION_JSON))
								.andExpect(status().isNotFound())
								.andReturn();
	}

}
