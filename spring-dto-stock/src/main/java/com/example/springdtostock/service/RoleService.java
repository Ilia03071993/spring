package com.example.springdtostock.service;

import com.example.springdtostock.dto.UserDto;
import com.example.springdtostock.entity.Role;
import com.example.springdtostock.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    @Transactional
    public void saveRole(String roleName) {
        Role role = new Role();
        role.setName(roleName);

        roleRepository.save(role);
    }

}
