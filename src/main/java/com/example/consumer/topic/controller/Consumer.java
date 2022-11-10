package com.example.consumer.topic.controller;

import com.example.consumer.fanout.model.Message;
import lombok.extern.slf4j.Slf4j;
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
    public void receiveA(Message message) {
        //log.info("Message received" + message);
        System.out.println("Message received: {}" + message);
    }

    @RequestMapping (method = RequestMethod.GET, value = "/receive" )
    public void receiveB() {
        Message message = (Message)rabbitTemplate.receiveAndConvert("queue.B");
        System.out.println("Message received: {}" + message);
        //log.info("Message received" + message);
    }

    @RabbitListener(queues = "queue.C")
    public void receiveC(Message message) {
        //log.info("Message received" + message);
        System.out.println("Message received: {}" + message);
    }
}
