package com.pj.planjourney.domain.plandetail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pj.planjourney.domain.plandetail.dto.EditPlanDetailResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisSubscriberPlanUpdates implements MessageListener {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            EditPlanDetailResponseDto response = objectMapper.readValue(message.getBody(), EditPlanDetailResponseDto.class);
            log.info(response.getPlanId() + " : onMessage");
            messagingTemplate.convertAndSend("/sub/room/" + response.getPlanId(), response);
        } catch (Exception e) {
            log.error("Error processing message", e);
        }
    }
}
