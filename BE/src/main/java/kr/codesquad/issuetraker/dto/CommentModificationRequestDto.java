package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentModificationRequestDto {
    private Long userId;
    private String content;
}