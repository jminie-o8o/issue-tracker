package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Issue;
import kr.codesquad.issuetraker.domain.label.Label;
import kr.codesquad.issuetraker.domain.milestone.Milestone;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class IssueDetailResponseDto {
    private Long issueId;
    private MilestoneInfoDto milestone;
    private Label label;
    private UserInfoDto author;
    private UserInfoDto assignee;
    private String title;
    private String description;
    private boolean isOpened;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static IssueDetailResponseDto of(Issue issue) {
       MilestoneInfoDto milestone = MilestoneInfoDto.of(issue.getMilestone());
       UserInfoDto author = UserInfoDto.of(issue.getAuthor());
       UserInfoDto assignee = UserInfoDto.of(issue.getAssignee());

       return IssueDetailResponseDto.builder()
               .issueId(issue.getId())
               .milestone(milestone)
               .label(issue.getLabel())
               .author(author)
               .assignee(assignee)
               .title(issue.getTitle())
               .description(issue.getDescription())
               .createdAt(issue.getCreatedAt())
               .modifiedAt(issue.getModifiedAt())
               .isOpened(issue.isOpened())
               .build();
    }

}
