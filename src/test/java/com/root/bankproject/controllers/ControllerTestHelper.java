package com.root.bankproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ControllerTestHelper {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    AccountsRepository accountsRepository;

    @BeforeEach
    public void clearDb(){
        usersRepository.deleteAll();
        accountsRepository.deleteAll();
    }


    public <T> T readingValue(MvcResult result, TypeReference<T> responseType) throws Exception {
        return objectMapper.readValue(result.getResponse().getContentAsString(),responseType);
    }


    public MvcResult performGet(String url) throws Exception {
        return mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

    }

    public <T> MvcResult performPost(String url, T dto) throws Exception {
        return           mockMvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn();
    }

    public MvcResult performPostAuth(String url,  String token) throws Exception {
        return mockMvc.perform(post(url)
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andReturn();
    }



}
