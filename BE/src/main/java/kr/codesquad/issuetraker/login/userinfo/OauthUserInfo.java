package kr.codesquad.issuetraker.login.userinfo;

import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.login.oauth.OauthClientType;
import lombok.AllArgsConstructor;

public class OauthUserInfo extends UserInfo {
    private OauthClientType oauthClientType;

    public OauthUserInfo(String email, String displayName, OauthClientType oauthClientType) {
        super(email, displayName);
        this.oauthClientType = oauthClientType;
    }

    @Override
    public User toUser() {
        return new User(email, displayName, null, oauthClientType);
    }
}
