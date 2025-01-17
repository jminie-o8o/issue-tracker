package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.login.jwt.JwtToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponseDto {
    private JwtToken accessToken;
    private JwtToken refreshToken;

    public static JwtResponseDto of(JwtToken accessToken, JwtToken refreshToken) {
        return new JwtResponseDto(accessToken, refreshToken);
    }
}
