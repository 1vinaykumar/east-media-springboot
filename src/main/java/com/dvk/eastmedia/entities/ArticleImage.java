package com.dvk.eastmedia.entities;


import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ArticleImage {
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private LocalDateTime uploadTime;
	private int priority;
	@Lob
	private byte[] imageFile;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Article article;
}
