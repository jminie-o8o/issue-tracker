package kr.codesquad.issuetraker.login.oauth;

import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class OauthClient {
    private String clientId;
    private String authServerUrl;
    private String resourceServerUrl;
    private String secretKey;

    protected OauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        this.clientId = clientId;
        this.authServerUrl = authServerUrl;
        this.resourceServerUrl = resourceServerUrl;
        this.secretKey = secretKey;
    }

    public OauthUserInfo getUserInfo(String authCode) {
        String accessToken = getAccessToken(authCode);
        String oauthResponse = getOauthResponse(accessToken);
        return convertToUserInfoFrom(oauthResponse);
    }

    private String getOauthResponse(String accessToken) {
        WebClient webClient = WebClient.create();
        return webClient.get()
                .uri(resourceServerUrl)
                .header("authorization", accessToken)
                .acceptCharset(StandardCharsets.UTF_8)
                .retrieve()
                .bodyToFlux(String.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
    }

    private String getAccessToken(String authCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String rawToken = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", secretKey)
                        .queryParam("code", authCode)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .ifNoneMatch("*")
                .ifModifiedSince(ZonedDateTime.now())
                .retrieve()
                .bodyToFlux(String.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        return parseToken(rawToken);
    }

    protected abstract String parseToken(String rawToken);
    protected abstract OauthUserInfo convertToUserInfoFrom(String rawInfo);
}
