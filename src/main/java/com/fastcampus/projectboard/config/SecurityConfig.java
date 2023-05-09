package com.fastcampus.projectboard.config;

import com.fastcampus.projectboard.dto.UserAccountDto;
import com.fastcampus.projectboard.dto.security.BoardPrincipal;
import com.fastcampus.projectboard.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        // 이렇게 설정을 하면 정적 파일들은 지나가지만 그 대신 Spring Security의 여러 보안 기능을 받을 수 있기 때문에 권장한다
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers(
                                HttpMethod.GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().and()
                .logout()
                        .logoutSuccessUrl("/")
                        .and()
                .build();
    }

    // spring security 검사 대상에서 제외할 것들 설정하는 곳
    // ex) static resource, css - js
    // 이렇게 설정하면 경고를 준다 http filter에서 처리하라고 추천해준다
    // 여기서 이렇게 설정해서 ignore하면 security의 기능을 이용할 수 없게 되고 그러면 csrf 같은 보안 공격에 취약해질 수 있기 때문이다
    // @Bean
    // public WebSecurityCustomizer webSecurityCustomizer() {
    //     return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    // }


    // 실제 인증 데이터를 가져오는 로직을 구현하는 곳
    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: " + username));
    }

    // Spring Security 인증 기능을 이용할 때는 반드시 PasswordEncoder도 등록을 해줘야만 한다!!
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
