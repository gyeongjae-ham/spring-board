package com.fastcampus.springboard.service;

import com.fastcampus.springboard.domain.Article;
import com.fastcampus.springboard.domain.type.SearchType;
import com.fastcampus.springboard.dto.ArticleDto;
import com.fastcampus.springboard.dto.ArticleUpdateDto;
import com.fastcampus.springboard.repository.ArticleRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("비즈니스 로직 - 게시글")
@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {
    // Mock을 주입하는 대상에 @InjectMocks를 붙여주고
    @InjectMocks private ArticleService sut;
    // 그 외에는 @Mock
    @Mock private ArticleRepository articleRepository;

    @DisplayName("[Service] 게시글을 검색하면, 게시글 리스트를 반환한다")
    @Test
    void givenSearchParameters_whenSearchingArticles_thenReturnsArticleList() {
        // Given

        // When
        Page<ArticleDto> articles = sut.searchArticles(SearchType.TITLE, "search keyword");

        // Then
        Assertions.assertThat(articles).isNotNull();
    }

    @DisplayName("[Service] 게시글을 조회하면, 게시글을 반환한다")
    @Test
    void givenArticleId_whenSearchingArticle_thenReturnsArticle() {
        // Given

        // When
        ArticleDto article = sut.searchArticle(1L);

        // Then
        Assertions.assertThat(article).isNotNull();
    }

    @DisplayName("[Service] 게시글 정보를 입력하면, 게시글을 생성한다")
    @Test
    void givenArticleInfo_whenSavingArticle_thenSavesArticls() {
        // Given
        // 코드적인 명시를 위해서 적은 부분이라서 없어도 더 발전시키지 않는다면 없어도 테스트에 지장은 없다
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        sut.saveArticle(ArticleDto.of(LocalDateTime.now(), "Luca", "title", "content", "#spring"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("[Service] 게시글의 ID와 수정 정보를 입력하면, 게시글을 수정한다")
    @Test
    void givenArticleIdAndModifiedInfo_whenUpdatingArticle_thenUpdatesArticls() {
        // Given
        // 코드적인 명시를 위해서 적은 부분이라서 없어도 더 발전시키지 않는다면 없어도 테스트에 지장은 없다
        given(articleRepository.save(any(Article.class))).willReturn(null);

        // When
        sut.updateArticle(1L, ArticleUpdateDto.of("title", "content", "#spring"));

        // Then
        then(articleRepository).should().save(any(Article.class));
    }

    @DisplayName("[Service] 게시글의 ID를 입력하면, 게시글을 삭제한다")
    @Test
    void givenArticleId_whenDeletingArticle_thenDeletesArticls() {
        // Given
        // 코드적인 명시를 위해서 적은 부분이라서 없어도 더 발전시키지 않는다면 없어도 테스트에 지장은 없다
        willDoNothing().given(articleRepository).delete(any(Article.class));

        // When
        sut.deleteArticle(1L);

        // Then
        then(articleRepository).should().delete(any(Article.class));
    }

}
