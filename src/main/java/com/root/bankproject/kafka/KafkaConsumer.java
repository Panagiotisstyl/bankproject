package com.root.bankproject.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics="account.created",groupId="groupA")
    private void listenAccount(String jsonMessage){
        System.out.println("Received message:"+jsonMessage);
    }

    @KafkaListener(topics="user.created", groupId = "groupB")
    private void listenUser(String jsonMessage){
        System.out.println("Received message:"+jsonMessage);
    }
}
