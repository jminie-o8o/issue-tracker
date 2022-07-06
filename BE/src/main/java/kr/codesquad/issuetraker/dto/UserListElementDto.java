package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserListElementDto {
    private Long userId;
    private String displayName;
    private String profileImageUrl;

    public static UserListElementDto from(User user) {
        return new UserListElementDto(user.getId(), user.getDisplayName(), user.getProfileImageUrl());
    }
}
