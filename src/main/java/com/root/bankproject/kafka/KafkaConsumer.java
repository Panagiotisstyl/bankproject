package com.root.bankproject.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.entities.AccountAudit;
import com.root.bankproject.entities.UserAudit;
import com.root.bankproject.repositories.AccountAuditRepository;
import com.root.bankproject.repositories.UserAuditRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper;
    private final UserAuditRepository userAuditRepository;
    private final AccountAuditRepository accountAuditRepository;


    @KafkaListener(topics= {
            "account.created",
            "user.created",
            "deposit.money",
            "withdraw.money",
            "user.addedToAccount"
    }, groupId = "allTopics")

    private void listenToALlTopics(String jsonMessage){
        log.info("Received message"+jsonMessage);
        String[] parts=jsonMessage.split(",");


        if(parts.length==2){
            try {
                UserResponseDto dto = objectMapper.readValue(jsonMessage, UserResponseDto.class);
                UserAudit userAudit = UserAudit.builder()
                        .userId(dto.getId())
                        .username(dto.getUsername())
                        .build();
                userAuditRepository.save(userAudit);
            }
            catch(JsonMappingException e){
                log.error("Exception caught: "+ e.getMessage());
            }
            catch(JsonProcessingException e){
                log.error("Exception caught: "+ e.getMessage());
            }
        }
        else{
            try {
                AccountResponseDto acc = objectMapper.readValue(jsonMessage, AccountResponseDto.class);
                AccountAudit accountAudit= AccountAudit.builder()
                                .accountId(acc.getId())
                                .balance(acc.getBalance())
                                .userIds(acc.getUserIds())
                                .build();
                accountAuditRepository.save(accountAudit);
            }
            catch(JsonMappingException e){
                log.error("Exception caught: "+ e.getMessage());
            }
            catch(JsonProcessingException e){
                log.error("Exception caught: "+ e.getMessage());
            }
        }



    }


}
