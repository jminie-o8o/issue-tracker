package kr.codesquad.issuetraker.login.oauth;

import com.google.gson.Gson;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;
import lombok.Builder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Map;

public class KakaoOauthClient extends OauthClient {
    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
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
                        .queryParam("client_id", "9dc5e51153cd29428199781510c17a32")
                        .queryParam("redirect_url", "http://52.79.243.28:8080/login/oauth/callback")
                        .queryParam("code", authCode)
                        .queryParam("grant_type", "authorization_code")
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
        return String.format("Bearer %s", rawToken.split("" + (char)34)[3]);
    }

    @Override
    protected OauthUserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new OauthUserInfo(infoMap.get("email"), infoMap.get("nickname"), OauthClientType.KAKAO);
    }
}
