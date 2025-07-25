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

    @KafkaListener(topics="deposit.money", groupId = "groupC")
    private void listenAccounDeposit(String jsonMessage){
        System.out.println("Deposit money:"+jsonMessage);
    }

    @KafkaListener(topics="withdraw.money", groupId = "groupD")
    private void listenAccountWithdraw(String jsonMessage){
        System.out.println("Withdraw money:"+jsonMessage);
    }

    @KafkaListener(topics="user.addedToAccount", groupId = "groupE")
    private void listenAccountAddUser(String jsonMessage){
        System.out.println("Added user:"+jsonMessage);
    }



}
