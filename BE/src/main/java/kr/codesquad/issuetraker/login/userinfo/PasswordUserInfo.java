package kr.codesquad.issuetraker.login.userinfo;

import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.dto.PasswordLoginRequestDto;
import kr.codesquad.issuetraker.dto.UserRegisterRequestDto;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public class PasswordUserInfo extends UserInfo {


    private String password;

    public PasswordUserInfo(String email, String displayName, String password) {
        super(email, displayName);
        this.password = password;
    }

    public static PasswordUserInfo ofLoginRequest(PasswordLoginRequestDto requestDto) throws NoSuchAlgorithmException {
        return new PasswordUserInfo(
                requestDto.getEmail(),
                null,
                hashPassword(requestDto.getPassword()));
    }

    public static PasswordUserInfo ofRegisterRequest(UserRegisterRequestDto requestDto) throws NoSuchAlgorithmException {
        return new PasswordUserInfo(
                requestDto.getEmail(),
                requestDto.getDisplayName(),
                hashPassword(requestDto.getPassword()));
    }

    private static String hashPassword(String rawPassword) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(rawPassword.getBytes(StandardCharsets.UTF_8));
        return new String(md.digest(), StandardCharsets.UTF_8);
    }

    @Override
    public User toUser() {
        return new User(email, displayName, password, null);
    }
}
