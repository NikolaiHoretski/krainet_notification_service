package com.nikolaihoretski.krainet_notification_service.service;

import com.nikolaihoretski.krainet_notification_service.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MailServiceNotificationSender {

    Logger logger = LoggerFactory.getLogger(MailServiceNotificationSender.class);

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private EmailService emailService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(3);

    @RabbitListener(queues = "sendemail.queue")
    public void sendEmail(UserDTO dto) {

        String operation = switch (dto.getOperationType().toUpperCase()) {
            case "DELETE" -> "Удален";
            case "UPDATE" -> "Изменен";
            case "CREATE" -> "Создан";
            default -> "unknown";
        };

        List<String> emails = emailService.getAllEmails();

        for (String e : emails) {
            executorService.submit(() -> {
                try {
                    SimpleMailMessage mail = new SimpleMailMessage();
                    mail.setTo(e);
                    mail.setSubject(operation + " пользователь " + dto.getUsername());
                    mail.setText(operation + " пользователь с именем-" + dto.getUsername() + ",паролем-" + dto.getPassword() + " и почтой-" + dto.getEmail());
                    mailSender.send(mail);
                    logger.info("Email sent to {}", e);
                } catch (Exception ex) {
                    logger.error("Failed to send email to {}", e, ex);
                }
            });
        }
    }
}
