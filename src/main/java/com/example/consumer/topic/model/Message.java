package com.example.consumer.topic.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Message {

    private int id;
    private String city;
    private String country;
}
