package com.pj.planjourney.domain.notification.repository;

import com.pj.planjourney.domain.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
