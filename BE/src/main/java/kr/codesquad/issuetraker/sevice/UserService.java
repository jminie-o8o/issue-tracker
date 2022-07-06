package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.domain.user.UserRepository;
import kr.codesquad.issuetraker.dto.UserListElementDto;
import kr.codesquad.issuetraker.dto.UserListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public UserListResponseDto getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserListElementDto> userElements = users.stream()
                .map(UserListElementDto::from)
                .collect(Collectors.toList());
        return new UserListResponseDto(userElements);
    }
}
