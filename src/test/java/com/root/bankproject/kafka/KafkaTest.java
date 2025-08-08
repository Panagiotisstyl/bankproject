package com.root.bankproject.kafka;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.enums.TypeAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class) 
public class KafkaTest {


    @Mock
    private UserResponseDto userResponseDto;

    @Mock
    private KafkaTemplate<String, UserResponseDto> kafkaTemplateUserResponse;

    @Mock
    private KafkaConsumer kafkaConsumer;

    @Mock
    private KafkaTemplate<String, AccountResponseDto> kafkaTemplateAccountResponse;

    @Test
    public void UserResponseKafkaTest(){
        UserResponseDto dto=UserResponseDto.builder()
                .id(1)
                .username("test")
                .build();

        kafkaTemplateUserResponse.send("user.creatd", userResponseDto);

        String expected=dto.toString();

        kafkaConsumer.listenToAllTopics(expected);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaConsumer).listenToAllTopics(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo(expected));

    }

    @Test
    public void AccountResponseKafkaTest(){

        AccountResponseDto dto= AccountResponseDto.builder()
                .id(1)
                .typeAccount(TypeAccount.JOINT)
                .description("test")
                .balance(300.00)
                .userIds(new ArrayList<>())
                .build();

        kafkaTemplateAccountResponse.send("account.created", dto);

        String expected=dto.toString();

        kafkaConsumer.listenToAllTopics(expected);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(kafkaConsumer).listenToAllTopics(argumentCaptor.capture());
        assertThat(argumentCaptor.getValue(), equalTo(expected));
    }

}
