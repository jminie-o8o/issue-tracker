package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.milestone.Milestone;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MilestoneInfoDto {
    private Long milestoneId;
    private String milestoneTitle;

    public static MilestoneInfoDto of(Milestone milestone) {
        return new MilestoneInfoDto(milestone.getId(), milestone.getTitle());
    }
}
