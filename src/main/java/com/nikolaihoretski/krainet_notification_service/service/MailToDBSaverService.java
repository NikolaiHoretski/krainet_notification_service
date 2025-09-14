package com.nikolaihoretski.krainet_notification_service.service;

import com.nikolaihoretski.krainet_notification_service.dto.EmailDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailToDBSaverService {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "getemail.queue")
    public void saveEmail(EmailDTO emailDTO) {

        emailService.saveEmails(emailDTO.getEmails());

    }
}
