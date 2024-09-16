package com.example.consumer.service;

import com.example.consumer.dto.RepertoryDto;
import com.example.consumer.mapper.RepertoryMapper;
import com.example.consumer.repository.RepertoryRepository;
import com.example.consumer.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RepertoryService {
    private final RepertoryRepository repository;
    private final RepertoryMapper mapper;
    private final JsonUtils jsonUtils;

    public void saveRepertory(RepertoryDto repertoryDto) {
        repository.save(mapper.toEntity(repertoryDto));
    }

    public RepertoryDto consumerStr(String json) {
        return jsonUtils.fromJson(json, RepertoryDto.class);
    }
}
