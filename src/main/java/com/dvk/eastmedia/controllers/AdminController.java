package com.dvk.eastmedia.controllers;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dvk.eastmedia.entities.Article;
import com.dvk.eastmedia.entities.ArticleImage;
import com.dvk.eastmedia.exceptions.ArticleNotFound;
import com.dvk.eastmedia.repos.ArticleRepo;

@RestController
public class AdminController {
	
	private final ArticleRepo articleRepo;
	
	public AdminController(ArticleRepo articleRepo) {
		this.articleRepo = articleRepo;
	}
	
	@PostMapping("/articles")
	public ResponseEntity<Object> addArticle(@RequestBody Article article) {
		
		article.setCreatedOn(LocalDateTime.now());
		Article savedArticle = articleRepo.save(article);
		URI link = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(savedArticle.getId()).toUri();
		return ResponseEntity.created(link).build();
	}
	
	@PostMapping("/articles/{id}/upload/{priority}")
	public ResponseEntity<Object> uploadImage(@RequestBody MultipartFile file, @PathVariable("id") Integer id, @PathVariable("priority") int priority) throws IOException {
		Article article = articleRepo.findById(id).orElseThrow(() -> new ArticleNotFound("User with Id "+id+" Not Found"));
		ArticleImage articleImage = new ArticleImage();
		articleImage.setName(StringUtils.cleanPath(file.getOriginalFilename()));
		articleImage.setArticle(article);
		articleImage.setImageFile(file.getBytes());
		articleImage.setUploadTime(LocalDateTime.now());
		articleImage.setPriority(priority);
		return ResponseEntity.ok().build();
	}
}
