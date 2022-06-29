package kr.codesquad.issuetraker.login.oauth;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OauthClientMapper {
    private Map<OauthClientType, OauthClient> clientMap;

    public OauthClientMapper(Map<OauthClientType, OauthClient> clientMap) {
        this.clientMap = new HashMap<>(clientMap);
    }
    public Optional<OauthClient> getOauthClient(OauthClientType oauthClientType) {
        return null;
    }
}
