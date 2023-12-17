package com.example.esa_spring.jms;

import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.util.Letter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Sender {

    private final RabbitTemplate template;

    @Value("${esa.rabbitmq.exchange}")
    private String exchange;

    @Value("${esa.rabbitmq.routingKey}")
    private String routingKey;

    @Value("${esa.rabbitmq.routingKeyMail}")
    private String routingKeyMail;

    public void logging(Change change) {
        template.convertAndSend(exchange, routingKey, change);
        if (change.getChangeType() == ChangeType.DELETE)
            template.convertAndSend(exchange, routingKeyMail, new Letter(change, "delete"));
        System.out.println("Sent message: " + change);
    }

}
