package com.example.springlessondto.service;

import com.example.springlessondto.dto.UserDto;
import com.example.springlessondto.entity.User;
import com.example.springlessondto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

//    @Transactional(readOnly = true)
//    public List<User> getAllUsers() {
//        return userRepository.findAll();
//    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id = %d not found".formatted(id)));
        return new UserDto(user.getName(), user.getEmail());
    }

//    @Transactional
//    public void saveUser(User user) {
//        userRepository.save(user);
//    }
//
//    @Transactional
//    public void updateUserById(Integer id, User user) {
//        User updatableUser = userRepository.findById(id)
//                .orElseThrow(() -> new NoSuchElementException("User with id = %d not found".formatted(id)));
//        updatableUser.setName(user.getName());
//        updatableUser.setEmail(user.getEmail());
//        updatableUser.setPassword(user.getPassword());
//        userRepository.save(updatableUser);
//    }
//
//    @Transactional
//    public void deleteUser(Integer id) {
//        if (!userRepository.existsById(id)) {
//            throw new NoSuchElementException("User with id = %d not found".formatted(id));
//        }
//        userRepository.deleteById(id);
//    }
}
