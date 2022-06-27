package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IssueInfoDto {
    private Long issueId;
    private String title;

    public static IssueInfoDto of(Issue issue) {
        return new IssueInfoDto(issue.getId(), issue.getTitle());
    }
}
