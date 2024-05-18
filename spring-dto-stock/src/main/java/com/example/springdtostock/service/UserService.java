package com.example.springdtostock.service;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.entity.ApplicationUser;
import com.example.springdtostock.entity.Role;
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
//        roleRepository.findRoleByName(userDto.role())
//                .ifPresentOrElse(role -> {
//                            throw new RuntimeException("Role " + userDto.role() + " already exists");
//                        },
//                        () -> {
//
//                        });
        Role newRole = new Role();
        newRole.setName(userDto.role());

//        roleRepository.save(newRole);

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userDto.username());
        applicationUser.setRoles(Set.of(newRole));
        applicationUser.setPassword(passwordEncoder.encode(userDto.rawPassword()));

        userRepository.save(applicationUser);

    }

    @Transactional
    public void updatePassword(Integer id, String password) {
        ApplicationUser applicationUser = userRepository.findById(id).orElseThrow(() ->
                new NoSuchUserException("User with id = %d not found".formatted(id)));
        applicationUser.setPassword(passwordEncoder.encode(password));

        userRepository.save(applicationUser);
    }

    @Transactional
    public void addRoleByUserId(Integer id, String roleName) {
        ApplicationUser applicationUser = userRepository.findById(id).orElseThrow(() ->
                new NoSuchUserException("User with id = %d not found".formatted(id)));

        Role role = new Role();
        role.setName(roleName);

        applicationUser.getRoles().add(role);

        userRepository.save(applicationUser);
    }

    @Transactional
    public void deleteRoleByUserId(Integer userId, Integer roleId) {
        userRepository.findById(userId).ifPresent(
                applicationUser -> {
                    applicationUser
                            .getRoles()
                            .remove(roleRepository.findById(roleId).orElseThrow());

                    userRepository.save(applicationUser);
                });
    }
}