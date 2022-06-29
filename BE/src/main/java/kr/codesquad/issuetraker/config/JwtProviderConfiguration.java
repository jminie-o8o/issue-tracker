package kr.codesquad.issuetraker.config;

import kr.codesquad.issuetraker.login.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtProviderConfiguration {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider(jwtSecret);
    }
}
