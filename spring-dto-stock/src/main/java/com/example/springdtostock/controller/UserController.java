package com.example.springdtostock.controller;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/user/save")
    public ResponseEntity<?> save(@RequestBody UserDto userDto) {
        userService.createUser(userDto);

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/user/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userService.updatePassword(id, userDto.rawPassword());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/api/user/{id}/update-role")
    public ResponseEntity<?> addRoleByUserId(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userService.addRoleByUserId(id, userDto.role());

        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/api/user/{userId}/role/{roleId}/remove")
    public ResponseEntity<?> deleteRoleByUserId(@PathVariable Integer userId,
                                                @PathVariable Integer roleId) {
        userService.deleteRoleByUserId(userId, roleId);

        return ResponseEntity.ok().build();
    }
}