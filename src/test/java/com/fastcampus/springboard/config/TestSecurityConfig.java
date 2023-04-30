package com.fastcampus.springboard.config;

import com.fastcampus.springboard.domain.UserAccount;
import com.fastcampus.springboard.repository.UserAccountRepository;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@Import(SecurityConfig.class)
public class TestSecurityConfig {

    @MockBean private UserAccountRepository userAccountRepository;

    // 각 테스트 메서드가 실행하기 전에 이 메서드를 실행시켜주라는 에노테이션
    @BeforeTestMethod
    public void securitySetUp() {
        given(userAccountRepository.findById(anyString())).willReturn(Optional.of(UserAccount.of(
                "lucaTest",
                "pw",
                "luca-test@email.com",
                "luca-test",
                "test-memo"
        )));
    }
}
