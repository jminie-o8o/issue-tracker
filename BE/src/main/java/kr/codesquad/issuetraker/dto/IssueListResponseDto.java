package kr.codesquad.issuetraker.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IssueListResponseDto {
    private List<IssueListElementDto> issues;

    public IssueListResponseDto(List<IssueListElementDto> issues) {
        this.issues = new ArrayList<>(issues);
    }
}
