package com.pj.planjourney.domain.notification.service;

import com.pj.planjourney.domain.notification.entity.Notification;
import com.pj.planjourney.domain.notification.repository.NotificationRepository;
import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.domain.user.repository.UserRepository;
import com.pj.planjourney.global.common.exception.BusinessLogicException;
import com.pj.planjourney.global.common.exception.ExceptionCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    // 알림 생성
    @Transactional
    public void createNotification(String message, String noticeType, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));
        Notification notification = new Notification(message, noticeType, user);
        notificationRepository.save(notification);
    }

    // 친구 요청 알림 생성
    @Transactional
    public void sendFriendRequestNotification(Long recipientId, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.SENDER_NOT_FOUND));
        String message = sender.getNickname() + "님이 친구 요청을 보냈습니다.";
        createNotification(message, "FRIEND_REQUEST", recipientId);
    }

    // 친구 요청 수락 알림 생성
    @Transactional
    public void sendFriendAcceptedNotification(Long recipientId, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.SENDER_NOT_FOUND));
        String message = sender.getNickname() + "님이 친구 요청을 수락했습니다.";
        createNotification(message, "FRIEND_ACCEPTED", recipientId);
    }

    // 친구 요청 거절 알림 생성
    @Transactional
    public void sendFriendRejectedNotification(Long recipientId, Long senderId) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.SENDER_NOT_FOUND));
        String message = sender.getNickname() + "님이 친구 요청을 거절했습니다.";
        createNotification(message, "FRIEND_REJECTED", recipientId);
    }



}
