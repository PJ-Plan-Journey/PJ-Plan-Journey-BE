package com.pj.planjourney.domain.notification.repository;

import com.pj.planjourney.domain.notification.entity.Notification;
import com.pj.planjourney.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserAndIsReadFalse(User user);
}
