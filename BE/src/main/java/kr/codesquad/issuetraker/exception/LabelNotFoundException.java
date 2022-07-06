package kr.codesquad.issuetraker.exception;

public class LabelNotFoundException extends ResourceNotFoundException {
    public LabelNotFoundException() {
        super("요청하신 id에 해당하는 라벨 정보를 찾을 수 없습니다.");
    }
}
