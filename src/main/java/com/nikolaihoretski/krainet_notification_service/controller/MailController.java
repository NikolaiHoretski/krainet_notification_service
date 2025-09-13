package com.nikolaihoretski.krainet_notification_service.controller;

import com.nikolaihoretski.krainet_notification_service.dto.EmailDetails;
import com.nikolaihoretski.krainet_notification_service.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMail(@RequestBody EmailDetails details) {
        mailService.sendEmail(details.getTo(), details.getSubject(), details.getBody());

        return ResponseEntity.ok("Email send successfully");
    }

}
