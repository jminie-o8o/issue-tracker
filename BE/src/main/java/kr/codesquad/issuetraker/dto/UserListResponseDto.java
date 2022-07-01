package kr.codesquad.issuetraker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UserListResponseDto {
    private List<UserListElementDto> users;

    public UserListResponseDto(List<UserListElementDto> users) {
        this.users = new ArrayList<>(users);
    }
}
