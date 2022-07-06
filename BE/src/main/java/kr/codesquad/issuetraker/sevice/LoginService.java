package kr.codesquad.issuetraker.sevice;

import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.domain.user.UserRepository;
import kr.codesquad.issuetraker.dto.*;
import kr.codesquad.issuetraker.exception.InvalidOauthClientNameException;
import kr.codesquad.issuetraker.exception.PasswordNotMatchedException;
import kr.codesquad.issuetraker.exception.UserNotFoundException;
import kr.codesquad.issuetraker.login.jwt.JwtProvider;
import kr.codesquad.issuetraker.login.jwt.JwtToken;
import kr.codesquad.issuetraker.login.jwt.JwtTokenType;
import kr.codesquad.issuetraker.login.oauth.OauthClient;
import kr.codesquad.issuetraker.login.oauth.OauthClientMapper;
import kr.codesquad.issuetraker.login.userinfo.OauthUserInfo;
import kr.codesquad.issuetraker.login.userinfo.PasswordUserInfo;
import kr.codesquad.issuetraker.login.userinfo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final OauthClientMapper oauthClientMapper;
    private final JwtProvider jwtProvider;

    public JwtResponseDto loginWithOauth(OauthLoginRequestDto requestDto) {
        OauthClient oauthClient = oauthClientMapper.getOauthClient(requestDto.getOauthClientName())
                .orElseThrow(() -> new InvalidOauthClientNameException());
        OauthUserInfo userInfo = oauthClient.getUserInfo(requestDto.getAuthCode());

        User user = userRepository.findByEmail(userInfo.getEmail())
                .orElseGet(() -> registerUser(userInfo));

        JwtToken accessToken = jwtProvider.createToken(user, JwtTokenType.ACCESS);
        JwtToken refreshToken = jwtProvider.createToken(user, JwtTokenType.REFRESH);
        return JwtResponseDto.of(accessToken, refreshToken);
    }

    public JwtResponseDto loginWithPassword(PasswordLoginRequestDto requestDto) throws NoSuchAlgorithmException {
        PasswordUserInfo userInfo = PasswordUserInfo.ofLoginRequest(requestDto);
        User userInDatabase = userRepository.findByEmail(userInfo.getEmail())
                .orElseThrow(() -> new UserNotFoundException());
        validatePassword(userInDatabase, userInfo);

        JwtToken accessToken = jwtProvider.createToken(userInDatabase, JwtTokenType.ACCESS);
        JwtToken refreshToken = jwtProvider.createToken(userInDatabase, JwtTokenType.REFRESH);
        return JwtResponseDto.of(accessToken, refreshToken);
    }

    private void validatePassword(User user, PasswordUserInfo userInfo) {
        if (!user.isSamePassword(userInfo.getPassword())) {
            throw new PasswordNotMatchedException();
        }
    }

    public GeneralResponseDto registerUserWithPassword(UserRegisterRequestDto requestDto) throws NoSuchAlgorithmException {
        PasswordUserInfo userInfo = PasswordUserInfo.ofRegisterRequest(requestDto);
        User registeredUser = registerUser(userInfo);
        return new GeneralResponseDto(200, "회원가입에 성공했습니다.");
    }

    private User registerUser(UserInfo userInfo) {
        User user = userInfo.toUser();
        return userRepository.save(user);
    }
}
