package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Issue;
import kr.codesquad.issuetraker.domain.milestone.Milestone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class MilestoneListResponseDto {
    private Long milestoneId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private int openedIssuesCount;
    private int closedIssuesCount;

    public static MilestoneListResponseDto of(Milestone milestone) {
        List<Issue> issues = milestone.getIssues();
        int openedIssuesCount = (int) issues.stream().filter(Issue::isOpened).count();
        int closedIssueCount = issues.size() - openedIssuesCount;

        return MilestoneListResponseDto.builder()
                .milestoneId(milestone.getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .openedIssuesCount(openedIssuesCount)
                .closedIssuesCount(closedIssueCount)
                .build();
    }
}
