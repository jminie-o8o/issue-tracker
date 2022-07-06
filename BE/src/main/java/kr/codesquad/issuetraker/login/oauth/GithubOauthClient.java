package kr.codesquad.issuetraker.login.oauth;

import com.google.gson.Gson;
import kr.codesquad.issuetraker.dto.GithubAccessTokenResponseDto;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;

@Slf4j
public class GithubOauthClient extends OauthClient {
    public GithubOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
    }

    @Override
    protected String getAccessToken(String authCode) {
        WebClient webClient = WebClient.builder()
                .baseUrl(authServerUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        GithubAccessTokenResponseDto rawToken = webClient.post()
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
                .bodyToFlux(GithubAccessTokenResponseDto.class)
                .toStream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException());
        return parseToken(rawToken.getAccessToken());
    }

    @Override
    protected String parseToken(String rawToken) {
        return String.format("token %s", rawToken);
    }

    @Override
    protected OauthUserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new OauthUserInfo(infoMap.get("login"), infoMap.get("login"), OauthClientType.GITHUB);
    }
}
