package com.pj.planjourney.domain.plandetail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.plandetail.dto.EditPlanDetailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisPublisher {

    private final RedisTemplate<String, Object> pubSubRedisTemplate;
    private final ObjectMapper objectMapper;

    public void publish(EditPlanDetailResponseDto message) {
        try {
            String response = objectMapper.writeValueAsString(message);
            pubSubRedisTemplate.convertAndSend("planUpdates", response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
