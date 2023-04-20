package com.fastcampus.springboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter // 전체 레벨에서는 Setter를 설정하지 말자(데이터 보호를 위해서 필요한 값만)
@ToString // 쉽게 볼 수 있도록
@Table(indexes = { // 빠르게 검색할 수 있도록 인덱스 설정
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String title; // 제목
    @Setter
    @Column(nullable = false, length = 10000) // nullable = true가 기본설정
    private String content; // 본문

    // @Column은 기본 설정을 변경하는 경우가 아니라면 생략이 가능하다
    @Setter
    private String hashtag; // 해시태그

    // Jpa 양방향 설정
    // mappedBy를 하지 않으면 양방향 관계의 두 테이블명을 합쳐서 테이블을 생성한다
    // mappedBy로 article 테이블로부터 온 것이라고 표시해 주기
    @ToString.Exclude // 순환 참조의 가능성이 있으므로 양방향 중에서 보통 one의 쪽에서 끊어준다
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시
    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    protected Article() {
    }

    // private 제어자로 막아놓고 팩토리 메서드로 편리하게 바로 생성하자
    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 팩토리 메서드
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    // 그냥 lombok의 EqualsAndHashCode를 사용하면 전체 필드를 비교하기 때문에
    // Entity 클래스에서는 고유값인 id만 가지고 비교하기 위해서 따로 생성해 줌
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
