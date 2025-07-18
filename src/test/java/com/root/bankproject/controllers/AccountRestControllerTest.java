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
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RequiredArgsConstructor
public class AccountRestControllerTest extends ControllerTestHelper{

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private AccountFactory accountFactory;

    @Autowired
    private AccountsConverter accountsConverter;

    @Test
    public void testCreateAccount() throws Exception{
        AccountsDto accountsDto=accountFactory.createAccountsDto(TypeAccount.SINGLE,"basic account",300.00);
        var result=performPost("/api/v1/accounts",accountsDto);
        var returnedAcc=readingValue(result,new TypeReference<Response<AccountResponseDto>>() {});

        assertThat(returnedAcc.getData().getTypeAccount()).isEqualTo(accountsDto.getTypeAccount());
        assertThat(returnedAcc.getData().getBalance()).isEqualTo(accountsDto.getBalance());
        assertThat(returnedAcc.getData().getDescription()).isEqualTo(accountsDto.getDescription());

        for(int i=0;i<accountsDto.getIds().size();i++){
            assertThat(returnedAcc.getData().getUserIds().get(i)).isEqualTo(accountsDto.getIds().get(i));
        }

        assertThat(accountsRepository.findAll()).hasSize(1);

        //INSERT WRONG USER ID
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountFactory.createAccountsDtoWrongUser(TypeAccount.SINGLE,"basic",300.99))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.data").value("User not found"));
    }

    @Test
    public void testfindAll() throws Exception{

        Account acc1=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
        Account acc2=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account2",400.00));

        List<AccountResponseDto> actualAccounts=List.of(accountsConverter.toResponseDto(acc1),accountsConverter.toResponseDto(acc2));

        var result=performGet("/api/v1/accounts");
        var returnedAcc=readingValue(result,new TypeReference<Response<List<AccountResponseDto>>>() {});

        assertThat(accountsRepository.findAll()).hasSize(2);

        for(int i=0;i<2;i++){
            AccountResponseDto accountResponseDto=actualAccounts.get(i);
            AccountResponseDto expectedAccs=returnedAcc.getData().get(i);

            assertThat(accountResponseDto.getTypeAccount()).isEqualTo(expectedAccs.getTypeAccount());
            assertThat(accountResponseDto.getBalance()).isEqualTo(expectedAccs.getBalance());
            assertThat(accountResponseDto.getDescription()).isEqualTo(expectedAccs.getDescription());
            assertThat(accountResponseDto.getId()).isEqualTo(expectedAccs.getId());
        }
    }

    @Test
    public void testfindById() throws Exception{

        Account acc1=accountsRepository.save(accountFactory.createAccount(TypeAccount.SINGLE,"basic account",300.00));
        AccountResponseDto accountResponseDto=accountsConverter.toResponseDto(acc1);
        var result=performGet("/api/v1/accounts/"+accountResponseDto.getId());
        var returnedAcc=readingValue(result,new TypeReference<Response<AccountResponseDto>>() {});

        assertThat(returnedAcc.getData().getTypeAccount()).isEqualTo(accountResponseDto.getTypeAccount());
        assertThat(returnedAcc.getData().getBalance()).isEqualTo(accountResponseDto.getBalance());
        assertThat(returnedAcc.getData().getDescription()).isEqualTo(accountResponseDto.getDescription());
        assertThat(returnedAcc.getData().getId()).isEqualTo(accountResponseDto.getId());

        //HADNLING EXCEPTION

        mockMvc.perform(get("/api/v1/accounts/1231"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.data").value("Account not found"));
    }

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

        //WRONG TOKEN
        mockMvc.perform(post("/api/v1/accounts/addUser/"+ acc.getId()+"/"+userToAdd.getId())
                            .header("Authorization","asdasdasdasd" ))
                    .andExpect(status().isUnauthorized())
                    .andReturn();


    }

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
