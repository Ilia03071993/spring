package com.example.springdtostock.controller;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.entity.ApplicationUser;
import com.example.springdtostock.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final ApplicationUserRepository userRepository;

    @GetMapping("/register")
    public void createUser(@RequestBody UserDto userDto) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userDto.username());
        applicationUser.setRole(userDto.role());
        applicationUser.setPassword(passwordEncoder.encode(userDto.rawPassword()));

        userRepository.save(applicationUser);
    }

}