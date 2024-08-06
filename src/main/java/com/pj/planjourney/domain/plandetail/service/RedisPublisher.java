package com.pj.planjourney.domain.plandetail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.plandetail.dto.EditPlanDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(String topic, EditPlanDetailResponseDto message) {
        try {
            String response = objectMapper.writeValueAsString(message);
            redisTemplate.convertAndSend("planUpdates", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
