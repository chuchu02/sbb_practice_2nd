package com.mysite.sbb.article.dao;


import com.mysite.sbb.article.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}