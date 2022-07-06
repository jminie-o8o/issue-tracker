package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MilestoneBulkDeleteRequestDto {
    private List<Long> milestoneIds;
}
