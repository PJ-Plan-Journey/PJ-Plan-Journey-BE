package com.pj.planjourney.domain.notification.controller;

import com.pj.planjourney.domain.notification.dto.NotificationListsDto;
import com.pj.planjourney.domain.notification.service.NotificationService;
import com.pj.planjourney.global.common.response.ApiResponse;
import com.pj.planjourney.global.common.response.ApiResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;


    // 친구 초대 알림 생성
    @PostMapping("/friend-invite")
    public ApiResponse<Void> sendFriendInviteNotification(@RequestParam Long recipientId,
                                                          @RequestParam Long senderId) {
        notificationService.sendFriendInviteNotification(recipientId, senderId);
        return new ApiResponse<>(null, ApiResponseMessage.SUCCESS);
    }


    // 여행 하루 전 리마인드 알림 생성
    @PostMapping("/travel-reminder")
    public ApiResponse<Void> sendTravelReminderNotification(@RequestParam Long userId,
                                                            @RequestParam String message) {
        notificationService.sendTravelReminderNotification(userId, message);
        return new ApiResponse<>(null, ApiResponseMessage.SUCCESS);
    }

    // 읽지 않은 알림 목록 조회
    @GetMapping("/unread")
    public ApiResponse<List<NotificationListsDto>> getUnreadNotifications(@RequestParam Long userId) {
        List<NotificationListsDto> notifications = notificationService.getUnreadNotifications(userId);
        return new ApiResponse<>(notifications, ApiResponseMessage.SUCCESS);
    }

    // 알림 읽음 처리
    @PatchMapping("/{id}/read")
    public ApiResponse<Void> markNotificationAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return new ApiResponse<>(null, ApiResponseMessage.SUCCESS);
    }


}
