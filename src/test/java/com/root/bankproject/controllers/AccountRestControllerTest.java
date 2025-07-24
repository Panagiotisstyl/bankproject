package com.root.bankproject.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.dtos.UsersDto;
import com.root.bankproject.encryption.BcryptHashing;
import com.root.bankproject.entities.Account;
import com.root.bankproject.entities.User;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.factories.AccountFactory;
import com.root.bankproject.factories.UserFactory;
import com.root.bankproject.repositories.AccountsRepository;
import com.root.bankproject.repositories.UsersRepository;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RequiredArgsConstructor
public class AccountRestControllerTest extends ControllerTestHelper{

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UsersService usersService;


    @Nested
    class createAccount{
        @Test
        public void testCreateAccount() throws Exception{
            AccountsDto accountsDto=accountFactory.createAccountsDto(TypeAccount.SINGLE,"basic account",300.00);
            var result=performPost("/api/v1/accounts",accountsDto);
            var returnedAcc=readingValue(result,new TypeReference<Response<AccountResponseDto>>() {});

            assertThat(returnedAcc.getData().getTypeAccount()).isEqualTo(accountsDto.getTypeAccount());
            assertThat(returnedAcc.getData().getBalance()).isEqualTo(accountsDto.getBalance());
            assertThat(returnedAcc.getData().getDescription()).isEqualTo(accountsDto.getDescription());

            Set<Tuple> expectedUsers=Set.of(tuple(returnedAcc.getData().getUserIds()));



            Account accDb=accountsRepository.findById(returnedAcc.getData().getId()).get();

            Set<Tuple> actualUsers=Set.of(tuple(usersService.findByAccountId(accDb.getId()).stream()
                    .map(User::getId).collect(Collectors.toList())));

            assertThat(expectedUsers).containsAll(actualUsers);
            assertThat(returnedAcc.getData().getTypeAccount()).isEqualTo(accDb.getTypeAccount());
            assertThat(returnedAcc.getData().getBalance()).isEqualTo(accDb.getBalance());
            assertThat(returnedAcc.getData().getDescription()).isEqualTo(accDb.getDescription());
            assertThat(returnedAcc.getData().getUserIds()).contains(usersService.findByAccountId(accDb.getId()).get(0).getId());
            assertThat(returnedAcc.getData().getUserIds()).contains(usersService.findByAccountId(accDb.getId()).get(1).getId());
            assertThat(accountsRepository.findAll()).hasSize(1);


        }

        @Test
        public void testCreateAccountWrongUser() throws Exception{
            mockMvc.perform(post("/api/v1/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(accountFactory.createAccountsDtoWrongUser(TypeAccount.SINGLE,"basic",300.99))))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.data").value("User not found"));
        }
    }

    @Nested
    class findAll{
        @Test
        public void testfindAll() throws Exception{

            Account acc1=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
            Account acc2=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account2",400.00));

            Set<Tuple> expectedTuples=Set.of(tuple(acc1.getId(),acc1.getBalance()
                    ,acc1.getDescription(), acc1.getTypeAccount()),tuple(acc2.getId(),acc2.getBalance()
                    ,acc2.getDescription(), acc2.getTypeAccount()));

            Set<Tuple> expectedUsers=Set.of(tuple(acc1.getUsers().stream().map(User::getId).collect(Collectors.toList())),
                    tuple(acc2.getUsers().stream().map(User::getId).collect(Collectors.toList())));

            var result=performGet("/api/v1/accounts");
            var returnedAcc=readingValue(result,new TypeReference<Response<List<AccountResponseDto>>>() {});

            assertThat(accountsRepository.findAll()).hasSize(2);

            Set<Tuple> actualTuples=returnedAcc.getData().stream()
                    .map(currentAcc->tuple(currentAcc.getId(),currentAcc.getBalance()
                    ,currentAcc.getDescription(),currentAcc.getTypeAccount())).collect(Collectors.toSet());

            Set<Tuple> actualUsers = returnedAcc.getData().stream()
                    .map(currentAcc -> tuple(currentAcc.getUserIds()))
                    .collect(Collectors.toSet());

            assertThat(expectedTuples).containsExactlyInAnyOrderElementsOf(actualTuples);
            assertThat(expectedUsers).containsExactlyInAnyOrderElementsOf(actualUsers);
        }
    }

    @Nested
    class findById{

        @Test
        public void testfindById() throws Exception{

            Account acc1=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
            var result=performGet("/api/v1/accounts/"+acc1.getId());
            var returnedAcc=readingValue(result,new TypeReference<Response<AccountResponseDto>>() {});

            assertThat(returnedAcc.getData().getTypeAccount()).isEqualTo(acc1.getTypeAccount());
            assertThat(returnedAcc.getData().getBalance()).isEqualTo(acc1.getBalance());
            assertThat(returnedAcc.getData().getDescription()).isEqualTo(acc1.getDescription());
            assertThat(returnedAcc.getData().getId()).isEqualTo(acc1.getId());

        }

        @Test
        public void testfindByIdWrongAccount() throws Exception{
            mockMvc.perform(get("/api/v1/accounts/1231"))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.data").value("Account not found"));
        }
    }

    @Nested
    class addUser{
        @Test
        public void testAddUser() throws Exception{
            //the user
            //"pan","panstyl@email.com","asa"
            User userToAdd=usersRepository.save(UserFactory.createUser("panas","stylia@email.com", BcryptHashing.hashPassword("asasa")));
            Account acc=accountsRepository.save(accountFactory.createAccount(TypeAccount.JOINT,"basic account",300.00));
            //log in to generate token automatically at the test
            UsersDto loginCred=UserFactory.createUsersDto("pan","panstyl@email.com","asa");
            var result=performPost("/api/v1/users/login",loginCred);
            var returnedResponse=readingValue(result,new TypeReference<Response<String>>(){});



            var resultAcc=performPostAuth("/api/v1/accounts/addUser/"+ acc.getId()+"/"+userToAdd.getId(),returnedResponse.getData());
            var returnedResponseAcc=readingValue(resultAcc,new TypeReference<Response<AccountResponseDto>>() {});

            assertThat(returnedResponseAcc.getData().getId()).isEqualTo(acc.getId());
            assertThat(returnedResponseAcc.getData().getUserIds()).contains(userToAdd.getId());

        }

        @Test
        public void testAddUserWrongToken() throws Exception{

            User userToAdd=usersRepository.save(UserFactory.createUser("panas","stylia@email.com", BcryptHashing.hashPassword("asasa")));
            Account acc=accountsRepository.save(accountFactory.createAccount(TypeAccount.JOINT,"basic account",300.00));
            mockMvc.perform(post("/api/v1/accounts/addUser/"+ acc.getId()+"/"+userToAdd.getId())
                            .header("Authorization","asdasdasdasd" ))
                    .andExpect(status().isUnauthorized())
                    .andReturn();

        }
    }

    @Nested
    class deposit{
        @Test
        public void testDeposit() throws Exception{

            Account acc=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
            usersRepository.save(UserFactory.createUser("panas","panstyl12@email.com",BcryptHashing.hashPassword("asa")));

            double balanceStart=acc.getBalance();

            UsersDto loginCred=UserFactory.createUsersDto("panas","panstyl12@email.com","asa");
            var result=performPost("/api/v1/users/login",loginCred);
            var returnedResponse=readingValue(result,new TypeReference<Response<String>>(){});

            var resultAcc=performPostAuth("/api/v1/accounts/deposit/"+acc.getId()+"/300.00",returnedResponse.getData());
            var returnedResponseAcc=readingValue(resultAcc,new TypeReference<Response<AccountResponseDto>>() {});

            double balanceEnd=returnedResponseAcc.getData().getBalance();

            assertThat(returnedResponseAcc.getData().getId()).isEqualTo(acc.getId());
            assertThat(balanceEnd>balanceStart).isTrue();

        }
    }

    @Nested
    class withdraw{
        @Test
        public void testWithdraw() throws Exception{

            Account acc=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
            double balanceStart=acc.getBalance();
            UsersDto loginCred=UserFactory.createUsersDto("pan","panstyl@email.com","asa");
            var result=performPost("/api/v1/users/login",loginCred);
            var returnedResponse=readingValue(result,new TypeReference<Response<String>>(){});

            var resultAcc=performPostAuth("/api/v1/accounts/withdraw/"+ acc.getId()+"/200.00",returnedResponse.getData());
            var returnedResponseAcc=readingValue(resultAcc,new TypeReference<Response<AccountResponseDto>>() {});

            double balanceEnd=returnedResponseAcc.getData().getBalance();

            assertThat(returnedResponseAcc.getData().getId()).isEqualTo(acc.getId());
            assertThat(balanceEnd<balanceStart).isTrue();

            //INSUFFICIENT BALANCE
            mockMvc.perform(post("/api/v1/accounts/withdraw/"+ acc.getId()+"/31000.00",returnedResponse.getData())
                            .header("Authorization",returnedResponse.getData() ))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.data").value("Insufficient balance"));


        }
    }

}
