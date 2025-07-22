package com.root.bankproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.converters.UsersConverter;
import com.root.bankproject.dtos.UserResponseDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.User;
import com.root.bankproject.factories.UserFactory;
import com.root.bankproject.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RequiredArgsConstructor
public class UsersRestControllerTest extends ControllerTestHelper{

    @Autowired
    private UsersRepository usersRepository;

    @Nested
    class registerUser{
        @Test
        public void testRegisterUser() throws Exception {
            UsersDto userDto= UserFactory.createUsersDto("pan","panstyl@email.com","asas");

            var result=performPost("/api/v1/users/register",userDto);
            var returnedEmployee=readingValue(result, new TypeReference<Response<UserResponseDto>>() {});

            assertThat(returnedEmployee.getData().getUsername()).isEqualTo(userDto.getUsername());

            assertThat(usersRepository.findAll()).hasSize(1);
        }
    }

    @Nested
    class findAll{
        @Test
        public void testFindAll() throws Exception {
            User user1=usersRepository.save(UserFactory.createUser("pan","styl","asas"));
            User user2=usersRepository.save(UserFactory.createUser("pana","styli","asasa"));


            Set<Tuple> expectedTuples=Set.of(tuple(user1.getId(),user1.getUsername())
                    ,tuple(user2.getId(),user2.getUsername()));


            var result=performGet("/api/v1/users");

            var expectedUsers=readingValue(result,new TypeReference<Response<List<UserResponseDto>>>(){});

            assertThat(usersRepository.findAll()).hasSize(2);

            Set<Tuple> actualTuples=expectedUsers.getData().stream()
                    .map(currentUser ->tuple(currentUser.getId(), currentUser.getUsername())).collect(Collectors.toSet());

            assertThat(actualTuples).containsExactlyInAnyOrderElementsOf(expectedTuples);
        }
    }


    @Nested
    class findById{

        @Test
        public void testFindById() throws Exception {
            User user=usersRepository.save(UserFactory.createUser("pan","styl","asas"));
            UserResponseDto userDto=UsersConverter.toResponseDto(user);
            var result=performGet("/api/v1/users/"+user.getId());
            var returnedUser=readingValue(result,new TypeReference<Response<UserResponseDto>>(){});

            assertThat(returnedUser.getData().getUsername()).isEqualTo(userDto.getUsername());
            assertThat(returnedUser.getData().getId()).isEqualTo(userDto.getId());
        }

        @Test
        public void testFindByIdWrongId() throws Exception {

            mockMvc.perform(get("/api/v1/users/1231"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.data").value("User not found"));
        }

    }

    @Nested
    class userLogin{

        @Test
        public void testUserLogin() throws Exception {
            User user=usersRepository.save(UserFactory.createUser("pan","styl@email.com", BcryptHashing.hashPassword("asas")));

            UsersDto loginCred=UserFactory.createUsersDto("pan","styl@email.com","asas");

            var result=performPost("/api/v1/users/login",loginCred);
            var returnedResponse=readingValue(result,new TypeReference<Response<String>>(){});

            String[] parts = returnedResponse.getData().split("\\|");
            assertThat(parts.length==3).isTrue();
            int userId=Integer.parseInt(parts[1]);
            assertThat(user.getId()).isEqualTo(userId);
        }

        @Test
        public void testUserLoginWrongPassword() throws Exception {
            usersRepository.save(UserFactory.createUser("pan","styl@email.com", BcryptHashing.hashPassword("asas")));

            UsersDto loginCredWrong=UserFactory.createUsersDto("pan","styl@email.com","asasas");
            var resultWrong=mockMvc.perform(post("/api/v1/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(loginCredWrong)))
                    .andExpect(status().isOk())
                    .andReturn();

            var returnedResponseWrong=readingValue(resultWrong,new TypeReference<Response<String>>(){});
            assertThat(returnedResponseWrong.getData()).isEqualTo("Wrong password");
        }
    }

}
