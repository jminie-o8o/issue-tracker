package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchFilterDto {
    private String title;
    private Boolean isOpened;
    private Long authorId;
    private Long labelId;
    private Long milestoneId;
}
