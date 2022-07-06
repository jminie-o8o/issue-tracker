package kr.codesquad.issuetraker.exception;

public class ExpiredJwtTokenException extends JwtException {
    public ExpiredJwtTokenException() {
        super("JWT 토큰이 만료되었습니다.");
    }
}
