package com.fastcampus.springboard.repository;

import com.fastcampus.springboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource // spring data rest 사용 어노테이션
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
