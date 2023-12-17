package com.example.esa_spring.jms;

import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.repositories.ChangeRepository;
import com.example.esa_spring.repositories.SubscribeRepository;
import com.example.esa_spring.service.EmailService;
import com.example.esa_spring.util.Letter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MailListener {

    @Value("${spring.mail.username}")
    private String from;

    private final EmailService emailService;
    private final SubscribeRepository subscribeRepository;

    @RabbitListener(queues = "${esa.rabbitmq.queueMail}")
    public void send(Letter letter) {
        System.out.println("WARNING WARNING WARNING");
        for (String to: subscribeRepository.getEmailsByNotify(letter.getNotify())) {
            emailService.sendEmail(letter.getChange(), from, to);
        }
    }
}
