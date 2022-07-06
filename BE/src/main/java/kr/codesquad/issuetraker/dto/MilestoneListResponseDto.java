package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MilestoneListResponseDto {
    private List<MilestoneListElementDto> milestones;

    public MilestoneListResponseDto(List<MilestoneListElementDto> milestones) {
        this.milestones = new ArrayList<>(milestones);
    }
}
