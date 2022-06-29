package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.login.oauth.OauthClientType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OauthLoginRequestDto {
    private String authCode;
    private OauthClientType oauthClientType;
}
