package kr.codesquad.issuetraker.login.userinfo;

import kr.codesquad.issuetraker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class UserInfo {
    protected String email;
    protected String displayName;

    public abstract User toUser();
}
