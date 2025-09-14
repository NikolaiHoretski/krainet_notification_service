package com.nikolaihoretski.krainet_notification_service.service;

import com.nikolaihoretski.krainet_notification_service.model.EmailEntity;
import com.nikolaihoretski.krainet_notification_service.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Transactional
    public void saveEmails(List<String> emails) {
        List<EmailEntity> entities = emails.stream()
                .distinct() // убираем дубликаты в приходящем списке
                .map(EmailEntity::new)
                .toList();
        emailRepository.saveAll(entities);
    }

    public List<String> getAllEmails() {
        return emailRepository.findAll().stream().map(EmailEntity::getEmail).toList();
    }
}
