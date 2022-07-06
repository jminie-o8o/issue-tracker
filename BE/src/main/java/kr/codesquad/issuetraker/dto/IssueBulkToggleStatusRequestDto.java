package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class IssueBulkToggleStatusRequestDto {
    private List<Long> issueIds;
}
