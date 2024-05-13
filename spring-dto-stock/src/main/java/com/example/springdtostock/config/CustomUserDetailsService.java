package com.example.springdtostock.config;

import com.example.springdtostock.entity.ApplicationUser;
import com.example.springdtostock.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ApplicationUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository
                .findApplicationUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '%s' not found".formatted(username)));

        return new SecurityUser(user);
    }
}