package com.nikolaihoretski.krainet_notification_service.repository;

import com.nikolaihoretski.krainet_notification_service.model.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    boolean existsByEmail(String email);
}
