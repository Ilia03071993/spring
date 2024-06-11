package com.example.springdtostock.dto;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public record UserTokenInfo(String username,
                            Collection<? extends GrantedAuthority> authorities) {

}
