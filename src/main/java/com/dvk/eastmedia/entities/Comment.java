package com.dvk.eastmedia.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue
	private Integer id;
	@Column(nullable = false)
	private String commentedBy;
	@Column(nullable = false)
	private String message;
	private LocalDateTime commentedAt;
	private int likes;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Article article;
}
