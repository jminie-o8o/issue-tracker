package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewIssueRequestDto {
    private Long mileStoneId;
    private Long labelId;
    private Long authorId;
    private Long assigneeId;
    private String title;
    private String description;
};
