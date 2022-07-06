package kr.codesquad.issuetraker.login.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import kr.codesquad.issuetraker.domain.user.User;
import kr.codesquad.issuetraker.exception.ExpiredJwtTokenException;
import kr.codesquad.issuetraker.exception.InvalidJwtTokenException;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Slf4j
public class JwtProvider {

    private final Key key;

    public JwtProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtToken createToken(User user, JwtTokenType tokenType) {
        Date now = new Date();
        Date expiresAt = new Date(now.getTime() + tokenType.getDuration());
        String token = Jwts.builder()
                .setIssuer("codesquad-team-5")
                .setIssuedAt(now)
                .setExpiration(expiresAt)
                .claim("tokenType", tokenType.name())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("displayName", user.getDisplayName())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return new JwtToken(token, expiresAt);
    }

    public boolean validateToken(String token) {
        return parseClaims(token) != null;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SecurityException e) {
            log.info("⚠️Invalid JWT signature.");
            throw new InvalidJwtTokenException();
        } catch (MalformedJwtException e) {
            log.info("⚠️Invalid JWT token.");
            throw new InvalidJwtTokenException();
        } catch (ExpiredJwtException e) {
            // TODO : 만료된 토큰 처리
            log.info("⚠️Expired JWT token.");
            throw new ExpiredJwtTokenException();
        } catch (UnsupportedJwtException e) {
            log.info("⚠️Unsupported JWT token.");
            throw new InvalidJwtTokenException();
        } catch (IllegalArgumentException e) {
            log.info("⚠️JWT token compact of handler are invalid.");
            throw new InvalidJwtTokenException();
        }
    }
}
