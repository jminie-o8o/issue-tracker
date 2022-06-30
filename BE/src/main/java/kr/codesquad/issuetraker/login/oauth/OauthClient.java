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
    protected String clientId;
    protected String authServerUrl;
    protected String resourceServerUrl;
    protected String secretKey;

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

    protected abstract String getAccessToken(String authCode);

    protected abstract String parseToken(String rawToken);
    protected abstract OauthUserInfo convertToUserInfoFrom(String rawInfo);
}
