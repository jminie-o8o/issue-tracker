package kr.codesquad.issuetraker.domain.issue;

import kr.codesquad.issuetraker.domain.label.Label;
import kr.codesquad.issuetraker.domain.milestone.Milestone;
import kr.codesquad.issuetraker.domain.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IssueModificationFields {
    private Milestone milestone;
    private Label label;
    private User author;
    private User assignee;
    private String title;
    private String description;
}


