package kr.codesquad.issuetraker.exception;

public class InvalidOauthClientNameException extends RuntimeException {
    public InvalidOauthClientNameException() {
        super("Oauth 클라이언트 정보가 올바르지 않습니다.");
    }
}
