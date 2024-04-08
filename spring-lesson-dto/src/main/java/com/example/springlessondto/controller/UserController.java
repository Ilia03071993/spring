package com.example.springlessondto.controller;

import com.example.springlessondto.dto.UserDto;
import com.example.springlessondto.entity.User;
import com.example.springlessondto.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {
    private final UserService userService;

//    @GetMapping()
//    public List<User> getAllUsers() {
//        return userService.getAllUsers();
//    }

    @GetMapping(value = "/{id}")
    public UserDto getUserById(@PathVariable Integer id) {
        return userService.getUserById(id);
    }

//    @PutMapping("/{id}")
//    public void updateUserById(@PathVariable Integer id, @RequestBody User user) {
//        userService.updateUserById(id, user);
//    }
//
//    @PostMapping
//    public void saveUser(@RequestBody User user) {
//        userService.saveUser(user);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteUser(@PathVariable Integer id) {
//        userService.deleteUser(id);
//    }
}
