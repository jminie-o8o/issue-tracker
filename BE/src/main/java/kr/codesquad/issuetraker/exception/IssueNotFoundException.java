package kr.codesquad.issuetraker.exception;

public class IssueNotFoundException extends ResourceNotFoundException {
    public IssueNotFoundException() {
        super("요청하신 id에 해당하는 이슈 정보를 찾을 수 없습니다.");
    }
}
