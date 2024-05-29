package com.example.springdtostock.service;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.entity.ApplicationUser;
import com.example.springdtostock.entity.Role;
import com.example.springdtostock.exception.NoSuchUserException;
import com.example.springdtostock.repository.ApplicationUserRepository;
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
    private final RoleService roleService;

    @Transactional
    public void createUser(UserDto userDto) {
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userDto.username());
        applicationUser.setRoles(Set.of(roleService.findRoleByName(userDto)));
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

        Role role = roleService.findRoleByName(userDto);
        applicationUser.getRoles().add(role);
        userRepository.save(applicationUser);
    }

    @Transactional
    public void deleteRoleByUserId(Integer userId, Integer roleId) {
        userRepository.findApplicationUserById(userId).ifPresent(
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