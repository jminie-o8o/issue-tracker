package kr.codesquad.issuetraker.config;

import kr.codesquad.issuetraker.login.oauth.OauthProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OauthPropertiesConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "oauth.github")
    public OauthProperties githubProperties() {
        return new OauthProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "oauth.kakao")
    public OauthProperties kakaoProperties() {
        return new OauthProperties();
    }
}


