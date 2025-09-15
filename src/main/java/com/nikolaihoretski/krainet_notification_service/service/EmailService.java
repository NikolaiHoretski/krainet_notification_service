package com.nikolaihoretski.krainet_notification_service.service;

import com.nikolaihoretski.krainet_notification_service.model.EmailEntity;
import com.nikolaihoretski.krainet_notification_service.repository.EmailRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    private final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private EmailRepository emailRepository;

    @Transactional
    public void saveEmails(List<String> emails) {

        List<String> existingEmails = emailRepository.findAll().stream()
                .map(EmailEntity::getEmail)
                .toList();
        List<EmailEntity> entities = emails.stream()
                .distinct()
                .filter(email -> !existingEmails.contains(email))
                .map(EmailEntity::new)
                .toList();

        if (!entities.isEmpty()) {
            emailRepository.saveAll(entities);
            logger.info("Всего  emails: '{}'", entities.size());
            entities.forEach(email -> logger.info("Email добавлен в базу: '{}'", email.toString()));
        } else {
            logger.info("добавление email откланено: он уже есть в базе");
        }

        List<String> alreadyExisting = emails.stream()
                .distinct()
                .filter(existingEmails::contains)
                .toList();

        if (!alreadyExisting.isEmpty()) {
            logger.info("Email которые уже есть в базе: {}", alreadyExisting);
        }
    }

    public List<String> getAllEmails() {
        return emailRepository.findAll().stream().map(EmailEntity::getEmail).toList();
    }
}
