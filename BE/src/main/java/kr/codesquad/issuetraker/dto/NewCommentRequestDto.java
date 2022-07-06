package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewCommentRequestDto {
    Long userId;
    String content;
}
