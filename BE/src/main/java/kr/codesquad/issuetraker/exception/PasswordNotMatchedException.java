package kr.codesquad.issuetraker.exception;

public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException() {
        super("id와 패스워드가 일치하지 않습니다.");
    }
}
