package com.example.esa_spring.jms;

import com.example.esa_spring.entity.Change;
import com.example.esa_spring.entity.ChangeType;
import com.example.esa_spring.repositories.ChangeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Listener {

    private final ChangeRepository changeRepository;

    @RabbitListener(queues = "${esa.rabbitmq.queue}")
    public void action(Change change) {
        System.out.println("Get change in DB: " + change);
        changeRepository.save(change);
    }

}
