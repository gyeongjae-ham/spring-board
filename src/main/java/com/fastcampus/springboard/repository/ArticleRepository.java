package com.fastcampus.springboard.repository;

import com.fastcampus.springboard.domain.Article;
import com.fastcampus.springboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // Entity 클래스 안의 모든 필드에 대한 기본 검사 기능 제공
        // 검색 기능을 커스터마이징하기 위한 기능 제공
        QuerydslBinderCustomizer<QArticle> {

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 선택한 필드들만 검색하도록 하는 메서드
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy); // 원하는 필드 선택

        // like '${v}'
        // bindings.bind(root.title).first(StringExpression::containsIgnoreCase);

        // like '%${v}%'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
    }
}
