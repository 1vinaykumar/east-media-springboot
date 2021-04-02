package com.dvk.eastmedia.controllers;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dvk.eastmedia.entities.Article;
import com.dvk.eastmedia.entities.Comment;
import com.dvk.eastmedia.exceptions.ArticleNotFound;
import com.dvk.eastmedia.repos.ArticleRepo;
import com.dvk.eastmedia.repos.CommentRepo;

@RestController
public class UserController {
	
	private final ArticleRepo articleRepo;
	private final CommentRepo commentRepo;
	
	public UserController(ArticleRepo articleRepo, CommentRepo commentRepo) {
		this.articleRepo = articleRepo;
		this.commentRepo = commentRepo;
		
	}
	
	@GetMapping("/articles")
	public List<Article> getArticles() {
		articleRepo.findAll().stream().sorted();
		return articleRepo.findAll();
	}
	
	@GetMapping("/articles/{id}")
	public Article getArticle(@PathVariable("id") Integer id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		return article;
	}
	
	@GetMapping("/articles/{id}/comments")
	public Set<Comment> getComments(@PathVariable("id") Integer id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		return article.getComments();
	}
	
	@PostMapping("/articles/{id}/comments")
	public ResponseEntity<Object> addComment(@RequestBody Comment comment, @PathVariable("id") Integer id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		comment.setArticle(article);
		comment.setCommentedAt(LocalDateTime.now());
		Comment savedComment = commentRepo.save(comment);
		URI link = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedComment.getId()).toUri();
		return ResponseEntity.created(link).build();
	}
	
	@PostMapping("/articles/{id}/view")
	public Article viewArticle(@PathVariable("id") Integer id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		article.setViews(article.getViews()+1);
		articleRepo.save(article);
		return article;
	}
	
	@PostMapping("/articles/{id}/like")
	public Article likeArticle(@PathVariable("id") Integer id) {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		article.setLikes(article.getLikes()+1);
		articleRepo.save(article);
		return article;
	
	}
}
