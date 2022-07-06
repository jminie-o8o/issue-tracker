package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.UserListResponseDto;
import kr.codesquad.issuetraker.sevice.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserListResponseDto> loadAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
