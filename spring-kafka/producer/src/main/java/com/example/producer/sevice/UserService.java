package com.example.producer.sevice;

import com.example.model.dto.UserDto;
import com.example.producer.entity.User;
import com.example.producer.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    @Value("${message.retention-period}")
    private int retentionPeriod;

    public UserDto sendMessageToKafka(UserDto userDto) {
        User user = new User(userDto.userId(),
                userDto.message(),
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(retentionPeriod)
        );
        return userMapper.toDto(user);
    }
}