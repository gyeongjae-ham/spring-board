package com.fastcampus.springboard.repository;

import com.fastcampus.springboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
