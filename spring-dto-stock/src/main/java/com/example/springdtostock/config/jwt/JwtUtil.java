package com.example.springdtostock.config.jwt;

import com.example.springdtostock.dto.UserTokenInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public String createAccessToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return createToken(username, authorities, jwtConfig.getTokenExpirationMs());
    }
    public String createRefreshToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return createToken(username, authorities, jwtConfig.getRefreshTokenExpirationMs());
    }

    private String createToken(String username,
                              Collection<? extends GrantedAuthority> authorities,
                              int expirationMs) {
        String token = Jwts.builder()
                .issuer("spring-security-app")
                .subject(username)
                .claim("authorities", authorities)
                .issuedAt(new Date()) //11-00
                //                     11:00              +    10
                .expiration(new Date(new Date().getTime() + expirationMs))
//                .expiration(new Date(new Date().getTime() + jwtConfig.getTokenExpirationMs()))
                .signWith(secretKey)
                .compact();

        return JwtConfig.TOKEN_PREFIX + token;
    }

    public UserTokenInfo validateToken(String accessToken) {
        accessToken = accessToken.replace(JwtConfig.TOKEN_PREFIX, "");

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload();

        String username = claims.get("username", String.class);

        return new UserTokenInfo(username, obtainAuthorities(claims));
    }

    @SuppressWarnings("unchecked")
    private Set<? extends GrantedAuthority> obtainAuthorities(Claims claims) {
        List<Map<String, String>> authorities = claims.get("authorities", List.class);

        return authorities
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                .collect(Collectors.toSet());
    }
}