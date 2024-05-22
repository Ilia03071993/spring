package com.example.springdtostock.controller;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/admin/users/save")
    public ResponseEntity<?> save(@RequestBody @Valid UserDto userDto) {
        userService.createUser(userDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/admin/users/{id}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userService.updatePassword(id, userDto);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/admin/users/{id}/update-role")
    public ResponseEntity<?> addRoleByUserId(@PathVariable Integer id, @RequestBody @Valid UserDto userDto) {
        userService.addRoleByUserId(id, userDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/admin/users/{userId}/role/{roleId}/remove")
    public ResponseEntity<?> deleteRoleByUserId(@PathVariable Integer userId,
                                                @PathVariable Integer roleId) {
        userService.deleteRoleByUserId(userId, roleId);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/admin/users/{id}/block")
    public ResponseEntity<?> blockUser(@PathVariable Integer id) {
        userService.blockUser(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/admin/users/{id}/unblock")
    public ResponseEntity<?> unblockUser(@PathVariable Integer id) {
        userService.unblockUser(id);

        return ResponseEntity.ok().build();
    }
}