package kr.codesquad.issuetraker.dto;

import kr.codesquad.issuetraker.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoDto {
    private Long userId;
    private String displayName;
    private String profileImageUrl;

    public static UserInfoDto of(User user) {
        return new UserInfoDto(user.getId(), user.getDisplayName(), user.getProfileImageUrl());
    }
}
