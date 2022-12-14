package com.example.consumer.simple;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "v1")
public class Consumer {

    private final RabbitMessagingTemplate rabbitMessagingTemplate;

    public Consumer(final RabbitMessagingTemplate rabbitMessagingTemplate) {
        this.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/receive")
    public String receive() {
          Message message = rabbitMessagingTemplate
                .receive("market-notifications");

        return message.toString();
    }

//    @RabbitListener(queues = "market-notifications")
//    public void logMessage(String message) {
//        System.out.println(message);
//    }
}
