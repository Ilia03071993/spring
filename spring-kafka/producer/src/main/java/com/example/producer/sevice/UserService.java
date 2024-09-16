package com.example.producer.sevice;

import com.example.producer.dto.UserDto;
import com.example.producer.entity.User;
import com.example.producer.mapper.UserMapper;
import com.example.producer.repository.UserRepository;
import com.example.producer.util.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    @Value("${message.retention-period}")
    private int retentionPeriod;
    private final JsonUtils jsonUtils;

    public UserDto saveUser(String message) {
        User user = new User(null, message, LocalDateTime.now(), LocalDateTime.now().plusMinutes(retentionPeriod));

        User savedUser = userRepository.save(user);

        UserDto userMapperDto = userMapper.toDto(savedUser);

        jsonUtils.toJson(userMapperDto);

        return userMapperDto;
    }
}
