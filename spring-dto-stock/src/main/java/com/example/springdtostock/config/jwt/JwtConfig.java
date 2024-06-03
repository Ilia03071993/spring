package com.example.springdtostock.config.jwt;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@ConfigurationProperties("jwt.config")
public class JwtConfig {
    public static final String HEADER = HttpHeaders.AUTHORIZATION;
    public static  final String TOKEN_PREFIX = "Bearer ";

    private String secret;
    private int tokenExpirationMs;
    private int refreshTokenExpirationMs;
}