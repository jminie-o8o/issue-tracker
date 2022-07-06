package kr.codesquad.issuetraker.domain.user;

import kr.codesquad.issuetraker.login.oauth.OauthClientType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "user_id")
    private Long id;
    private String email;
    private String displayName;
    private String password;
    private String profileImageUrl;
    @Enumerated(EnumType.STRING)
    private OauthClientType oauthClientType;

    public User(String email, String displayName, String password, OauthClientType oauthClientType) {
        this.email = email;
        this.displayName = displayName;
        this.password = password;
        this.oauthClientType = oauthClientType;
    }

    public boolean isSamePassword(String password) {
        return this.password.equals(password);
    }
}
