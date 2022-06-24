package kr.codesquad.issuetraker.exception;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException() {
        super("요청하신 id에 해당하는 유저 정보를 찾을 수 없습니다");
    }
}
