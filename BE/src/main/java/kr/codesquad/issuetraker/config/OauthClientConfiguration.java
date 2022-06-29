package kr.codesquad.issuetraker.config;

import kr.codesquad.issuetraker.login.oauth.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class OauthClientConfiguration {

    @Value("${github.client-id}")
    private String githubClientId;
    @Value("${github.auth-server-url}")
    private String githubAuthServerUrl;
    @Value("${github.resource-server-url}")
    private String githubResourceServerUrl;
    @Value("${github.secret-key}")
    private String githubSecretKey;

    @Value("${kakao.client-id}")
    private String kakaoClientId;
    @Value("${kakao.auth-server-url}")
    private String kakaoAuthServerUrl;
    @Value("${kakao.resource-server-url}")
    private String kakaoResourceServerUrl;
    @Value("${kakao.secret-key}")
    private String kakaoSecretKey;


    @Bean
    public OauthClientMapper oauthClientMapper() {
        GithubOauthClient github = new GithubOauthClient(githubClientId, githubAuthServerUrl, githubResourceServerUrl, githubSecretKey);
        KakaoOauthClient kakao = new KakaoOauthClient(kakaoClientId, kakaoAuthServerUrl, kakaoResourceServerUrl, kakaoSecretKey);

        Map<OauthClientType, OauthClient> clientMap = new HashMap<>();

        clientMap.put(OauthClientType.GITHUB, github);
        clientMap.put(OauthClientType.KAKAO, kakao);

        return new OauthClientMapper(clientMap);
    }
}
