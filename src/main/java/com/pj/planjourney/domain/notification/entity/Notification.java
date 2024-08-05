package com.pj.planjourney.domain.notification.entity;

import com.pj.planjourney.domain.user.entity.User;
import com.pj.planjourney.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "notifications")
public class Notification extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String message;

    private boolean isRead;

    private String noticeType;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Notification(String message, String noticeType, User user) {
        this.message = message;
        this.noticeType = noticeType;
        this.user = user;
        this.isRead = false;
    }
}
