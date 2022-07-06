package kr.codesquad.issuetraker.login;

import kr.codesquad.issuetraker.login.jwt.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwtToken = request.getHeader("authorization");
        jwtProvider.validateToken(jwtToken);

        return true;
    }
}
