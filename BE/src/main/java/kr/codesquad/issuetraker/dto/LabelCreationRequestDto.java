package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabelCreationRequestDto {
    private String name;
    private String description;
    private String backgroundColor;
}
