package kr.codesquad.issuetraker.dto;

import lombok.Getter;

@Getter
public class IssueModificationRequestDto {
    private Long mileStoneId;
    private Long labelId;
    private Long authorId;
    private Long assigneeId;
    private String title;
    private String description;



}



