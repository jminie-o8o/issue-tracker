package kr.codesquad.issuetraker.login.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OauthProperties {
    private String clientId;
    private String authServerUrl;
    private String resourceServerUrl;
    private String secretKey;
}
