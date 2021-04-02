package com.dvk.eastmedia.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dvk.eastmedia.entities.Article;

public interface ArticleRepo extends JpaRepository<Article, Integer> {

}
