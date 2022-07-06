package kr.codesquad.issuetraker.exception;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException() {
        super("요청하신 id에 해당하는 댓글 정보를 찾을 수 없습니다.");
    }
}
