package kr.codesquad.issuetraker.exception;

public class InvalidJwtTokenException extends JwtException {
    public InvalidJwtTokenException() {
        super("JWT 인증 토큰이 올바르지 않습니다.");
    }
}
