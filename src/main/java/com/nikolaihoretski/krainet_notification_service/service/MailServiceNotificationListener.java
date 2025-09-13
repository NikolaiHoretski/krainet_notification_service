package com.nikolaihoretski.krainet_notification_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceNotificationListener {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = "myQueue")
    public void sendEmail() {

        String[] emails = {"nikolai.horetski@gmail.com", "nikolai.horetski@yandex.by"};

        for (String email : emails) {

            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(email);
            mail.setSubject("Test");
            mail.setText("Hello");

            mailSender.send(mail);
        }
    }
}
