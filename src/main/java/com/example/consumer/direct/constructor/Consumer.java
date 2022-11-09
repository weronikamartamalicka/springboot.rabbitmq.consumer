package com.example.consumer.direct.constructor;

import lombok.extern.slf4j.Slf4j;
import com.example.consumer.direct.model.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/v1")
public class Consumer {

    private final RabbitTemplate rabbitTemplate;

    public Consumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "queue.A")
    public void receive(Message message) {
        //log.info("Message received" + message);
        System.out.println(message);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/receive" )
    public String receive() {
        Message message = (Message)rabbitTemplate.receiveAndConvert("queue.B");
        System.out.println(message);
        //log.info("Message received" + message);

        return "Message has been received";
    }
}
