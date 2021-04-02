package com.dvk.eastmedia.entities;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Article {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String title;
	private String content;
	private LocalDateTime createdOn;
	private int likes;
	private int views;
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<Comment> comments;
	@OneToMany(mappedBy = "article", fetch = FetchType.LAZY)
	private Set<ArticleImage> images;
}
