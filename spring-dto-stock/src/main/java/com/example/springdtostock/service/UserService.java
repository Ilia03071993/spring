package com.example.springdtostock.service;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.entity.ApplicationUser;
import com.example.springdtostock.exception.NoSuchUserException;
import com.example.springdtostock.repository.ApplicationUserRepository;
import com.example.springdtostock.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Transactional
    public void createUser(UserDto userDto) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userDto.username());
        applicationUser.setRoles(Set.of(roleRepository.findRoleByName(userDto.role()).orElseThrow()));
        applicationUser.setPassword(passwordEncoder.encode(userDto.rawPassword()));

        userRepository.save(applicationUser);

    }

    @Transactional
    public void updatePassword(Integer id, UserDto userDto) {
        ApplicationUser applicationUser = userRepository.findById(id).orElseThrow(() ->
                new NoSuchUserException("User with id = %d not found".formatted(id)));
        applicationUser.setPassword(passwordEncoder.encode(userDto.rawPassword()));

        userRepository.save(applicationUser);
    }

    @Transactional
    public void addRoleByUserId(Integer id, UserDto userDto) {
        ApplicationUser applicationUser = userRepository.findById(id).orElseThrow(() ->
                new NoSuchUserException("User with id = %d not found".formatted(id)));

        roleRepository.findRoleByName(userDto.role()).ifPresent(
                role -> {
                    applicationUser.getRoles().add(role);
                    userRepository.save(applicationUser);
                }
        );
    }

    @Transactional
    public void deleteRoleByUserId(Integer userId, Integer roleId) {
//        ApplicationUser user = userRepository.findById(userId)
//                .orElseThrow(() -> new NoSuchUserException("User with id = %d not found".formatted(userId)));
//        user.getRoles().removeIf(role -> role.getId().equals(roleId));
//        userRepository.save(user);
        userRepository.findById(userId).ifPresent(
                applicationUser -> {
                    applicationUser
                            .getRoles()
                            .removeIf(role -> role.getId().equals(roleId));

                    userRepository.save(applicationUser);
                });
    }

    @Transactional
    public void blockUser(Integer id) {
        userRepository.findById(id).ifPresent(
                applicationUser -> {
                    applicationUser.setBlocked(true);
                }
        );
    }

    @Transactional
    public void unblockUser(Integer id) {
        userRepository.findById(id).ifPresent(
                applicationUser -> {
                    applicationUser.setBlocked(false);
                }
        );
    }
}