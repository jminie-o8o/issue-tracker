package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Issue;
import kr.codesquad.issuetraker.domain.milestone.Milestone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
public class MilestoneDetailResponseDto {
    private Long milestoneId;
    private String title;
    private String description;
    private LocalDate dueDate;
    private List<IssueInfoDto> openedIssues;
    private List<IssueInfoDto> closedIssues;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static MilestoneDetailResponseDto of(Milestone milestone) {
        List<Issue> issues = milestone.getIssues();

        return MilestoneDetailResponseDto.builder()
                .milestoneId(milestone.getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .openedIssues(issues.stream().filter(issue-> issue.isOpened()).map(IssueInfoDto::of).collect(Collectors.toList()))
                .closedIssues(issues.stream().filter(issue-> !issue.isOpened()).map(IssueInfoDto::of).collect(Collectors.toList()))
                .createdAt(milestone.getCreatedAt())
                .modifiedAt(milestone.getModifiedAt())
                .build();
    }
}
