package com.root.bankproject.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics= {
            "account.created",
            "user.created",
            "deposit.money",
            "withdraw.money",
            "user.addedToAccount"
    }, groupId = "allTopics")
    private void listenToALlTopics(String jsonMessage){
        log.info("Received message"+jsonMessage);
    }


}
