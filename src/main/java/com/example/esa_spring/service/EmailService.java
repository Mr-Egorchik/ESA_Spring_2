package com.example.esa_spring.service;

import com.example.esa_spring.entity.Change;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendEmail(Change change, String from, String to) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(from);
        mailMessage.setTo(to);
        mailMessage.setSubject("DELETE NOTIFICATION");
        mailMessage.setText(change.toString());
        mailSender.send(mailMessage);
    }

}
