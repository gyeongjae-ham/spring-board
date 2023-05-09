package com.fastcampus.projectboard.repository.querydsl;

import com.fastcampus.projectboard.domain.Article;
import com.fastcampus.projectboard.domain.QArticle;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

// 이름에서 Impl은 querydsl에서 정해놓은 규칙이다!!
public class ArticleRepositoryCustomImpl extends QuerydslRepositorySupport implements ArticleRepositoryCustom {

    public ArticleRepositoryCustomImpl() {
        super(Article.class);
    }

    @Override
    public List<String> findAllDistinctHashtags() {
        QArticle article = QArticle.article;

        // 제너릭 타입을 return type과 꼭 일치시켜 주자!!
        return from(article)
                .distinct()
                .select(article.hashtag)
                .where(article.hashtag.isNotNull())
                .fetch();
    }

}
