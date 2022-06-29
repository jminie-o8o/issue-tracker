package kr.codesquad.issuetraker.login.oauth;

import com.google.gson.Gson;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;

import java.util.Map;

public class KakaoOauthClient extends OauthClient {
    public KakaoOauthClient(String clientId, String authServerUrl, String resourceServerUrl, String secretKey) {
        super(clientId, authServerUrl, resourceServerUrl, secretKey);
    }

    @Override
    protected String parseToken(String rawToken) {
        return rawToken.split("accessToken=")[1].split(",")[0];
    }

    @Override
    protected OauthUserInfo convertToUserInfoFrom(String rawInfo) {
        Map<String, String> infoMap = new Gson().fromJson(rawInfo, Map.class);
        return new OauthUserInfo(infoMap.get("email"), infoMap.get("name"), OauthClientType.KAKAO);
    }
}
