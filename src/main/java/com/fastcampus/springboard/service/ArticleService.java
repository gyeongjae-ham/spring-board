package com.fastcampus.springboard.service;

import com.fastcampus.springboard.domain.type.SearchType;
import com.fastcampus.springboard.dto.ArticleDto;
import com.fastcampus.springboard.dto.ArticleUpdateDto;
import com.fastcampus.springboard.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }

    public void saveArticle(ArticleDto dto) {

    }

    public void updateArticle(long id, ArticleUpdateDto dto) {

    }

    public void deleteArticle(long articleId) {

    }
}
