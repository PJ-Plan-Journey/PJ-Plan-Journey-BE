package com.pj.planjourney.domain.notification.dto;

import com.pj.planjourney.domain.notification.entity.Notification;
import lombok.Getter;

@Getter
public class NotificationListsDto {
    private Long id;
    private String message;
    private String noticeType;
    private boolean isRead;


    public NotificationListsDto(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.noticeType = notification.getNoticeType();
        this.isRead = notification.isRead();
    }
}
