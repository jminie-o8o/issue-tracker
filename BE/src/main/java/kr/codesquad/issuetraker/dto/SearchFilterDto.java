package kr.codesquad.issuetraker.dto;

import javafx.beans.binding.BooleanExpression;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchFilterDto {
    private Boolean isOpened;
    private Long authorId;
    private Long labelId;
    private Long milestoneId;
}
