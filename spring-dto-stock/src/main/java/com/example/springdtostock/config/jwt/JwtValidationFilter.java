package com.example.springdtostock.config.jwt;

import com.example.springdtostock.dto.ErrorResponse;
import com.example.springdtostock.dto.UserTokenInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtValidationFilter extends OncePerRequestFilter {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final JwtUtil jwtUtil;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) {
        String token = obtainToken(request);
        if (token != null) {
            try {
                UserTokenInfo userTokenInfo = jwtUtil.validateToken(token);
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        userTokenInfo, null, userTokenInfo.authorities()
                );

                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (ExpiredJwtException e) {
                handleException(response, "Token expired", e);
            } catch (SignatureException e) {
                handleException(response, "Invalid token signature", e);
            } catch (MalformedJwtException e) {
                handleException(response, "Malformed token", e);
            } catch (Exception e) {
                handleException(response, "Invalid token", e);
            }
        }

        chain.doFilter(request, response);
    }

    @SneakyThrows
    private void handleException(HttpServletResponse response, String message, Exception e) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        MAPPER.writeValue(response.getWriter(), new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), message, null));
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String url = request.getRequestURI();
        return "/login".equals(url);

    }

    private String obtainToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(JwtConfig.HEADER);
        if (authorizationHeader == null || !authorizationHeader.startsWith(JwtConfig.TOKEN_PREFIX)) {
            return null;
        }

        return authorizationHeader.replace(JwtConfig.TOKEN_PREFIX, "");
    }
}