package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.*;
import kr.codesquad.issuetraker.sevice.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login/oauth/callback")
    public String getOauthAuthCode() {
        return "URL 쿼리로 응답받은 auth 코드로 서버에 로그인 요청을 보내주세요.";
    }

    @PostMapping("/login/oauth")
    public ResponseEntity<JwtResponseDto> loginWithOauth(@RequestBody OauthLoginRequestDto requestDto) {
        return ResponseEntity.ok(loginService.loginWithOauth(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> loginWithPassword(@RequestBody PasswordLoginRequestDto requestDto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(loginService.loginWithPassword(requestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponseDto> registerWithPassword(@RequestBody UserRegisterRequestDto requestDto) throws NoSuchAlgorithmException {
        return ResponseEntity.ok(loginService.registerUserWithPassword(requestDto));
    }



}
