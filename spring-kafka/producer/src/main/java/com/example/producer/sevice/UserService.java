package com.example.producer.sevice;

import com.example.model.dto.UserDto;
import com.example.producer.client.AuditClient;
import com.example.producer.entity.User;
import com.example.producer.mapper.UserMapper;
import com.example.producer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuditClient auditClient;
    public UserDto saveUser(UserDto userDto) {
//        User user = new User(null,
//                message,
//                LocalDateTime.now(),
//                LocalDateTime.now().plusMinutes(retentionPeriod)
//        );
        User user = userMapper.toEntity(userDto);
        User savedUser = userRepository.save(user);
        auditClient.logAction("SAVE_MESSAGE", userDto.id(), "Message saved in Producer");

        return userMapper.toDto(savedUser);
    }
}