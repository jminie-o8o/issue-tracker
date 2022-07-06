package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.issue.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentListResponseDto {
    private Long commentId;
    private String authorName;
    private String content;
    private String authorProfileImageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public static CommentListResponseDto of(Comment comment) {
        return CommentListResponseDto.builder()
                .commentId(comment.getId())
                .authorName(comment.getAuthor().getDisplayName())
                .content(comment.getContent())
                .authorProfileImageUrl(comment.getAuthor().getProfileImageUrl())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build();
    }
}
