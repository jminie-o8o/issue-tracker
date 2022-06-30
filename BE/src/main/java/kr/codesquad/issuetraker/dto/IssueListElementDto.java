package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Issue;
import kr.codesquad.issuetraker.domain.label.Label;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IssueListElementDto {
    private Long issueId;
    private String title;
    private String description;
    private String milestoneTitle;
    private Label label;
    private Boolean isOpened;

    public static IssueListElementDto of(Issue issue) {
        return IssueListElementDto.builder()
                .issueId(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .milestoneTitle(issue.getMilestone().getTitle())
                .label(issue.getLabel())
                .isOpened(issue.isOpened())
                .build();
    }

}
