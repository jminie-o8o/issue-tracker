package kr.codesquad.issuetraker.login.oauth;

import com.google.gson.Gson;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;

import java.util.Map;

public class GithubOauthClient extends OauthClient {
    public GithubOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
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
