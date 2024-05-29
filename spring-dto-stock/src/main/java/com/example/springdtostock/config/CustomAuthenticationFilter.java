package com.example.springdtostock.config;

import com.example.springdtostock.dto.AuthRequest;
import com.example.springdtostock.dto.AuthenticationResultResponse;
import com.example.springdtostock.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final AuthenticationManager authManager;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new AuthenticationServiceException("Only POST method is allowed");
        }

        AuthRequest authRequest = MAPPER.readValue(request.getInputStream(), AuthRequest.class);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password()
        );
        return authManager.authenticate(authentication);
    }

    @SneakyThrows
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authResult);

        AuthenticationResultResponse resultResponse = new AuthenticationResultResponse(HttpStatus.OK.name(),
                authResult.getName());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        MAPPER.writeValue(response.getOutputStream(), resultResponse);
    }

    @SneakyThrows
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), failed.getMessage(), null);
        MAPPER.writeValue(response.getOutputStream(), errorResponse);
    }
}