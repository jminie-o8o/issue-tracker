package kr.codesquad.issuetraker.exception;

public class MilestoneNotFoundException extends ResourceNotFoundException {
    public MilestoneNotFoundException() {
        super("요청하신 id에 해당하는 마일스톤 정보를 찾을 수 없습니다.");
    }
}
