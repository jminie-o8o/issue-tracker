package kr.codesquad.issuetraker.login.oauth;

import com.google.gson.Gson;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;

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

    @Override
    protected String parseToken(String rawToken) {
        return String.format("token %s", rawToken.split("" + (char)34)[3]);
    }

    @Override
    protected OauthUserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new OauthUserInfo(infoMap.get("login"), infoMap.get("login"), OauthClientType.GITHUB);
    }
}
