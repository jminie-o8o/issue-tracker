package kr.codesquad.issuetraker.controller;

import kr.codesquad.issuetraker.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class LoginController {

    @GetMapping("/login/oauth/callback")
    public String getOauthAuthCode() {
        return "URL 쿼리로 응답받은 auth 코드로 서버에 로그인 요청을 보내주세요.";
    }



}
