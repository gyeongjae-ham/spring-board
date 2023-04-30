package com.fastcampus.springboard.config;

import com.fastcampus.springboard.dto.security.BoardPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing // JPA Auditing 기능 활성화
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // SecurityContextHolder : Security의 모든 정보를 들고 있는 class에서 getContext로
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                // SecurityContext를 불러온다. SecurityContext에 있는 Authenticate 정보를 가져와서
                .map(SecurityContext::getAuthentication)
                // Authenticated 됐는지 확인하고
                .filter(Authentication::isAuthenticated)
                // 로그인 정보인 보편적인 Principal을 꺼내온다
                // 어떤 인증 정보가 들어올지 몰라서 Object를 반환한다
                .map(Authentication::getPrincipal)
                // UserDetails를 구현한 Principal 구현체를 불러오고, 타입 캐스팅을 해준다
                .map(BoardPrincipal.class::cast)
                // username을 불러온다
                .map(BoardPrincipal::getUsername);
    }
}
