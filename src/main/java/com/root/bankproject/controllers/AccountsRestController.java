package com.root.bankproject.controllers;

import com.root.bankproject.ExceptionHandler.RequestsHandler;
import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.command.AccountCommand;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AccountsRestController implements AccountApi {

   private final AccountCommand accountCommand;
   private final RequestsHandler requestsHandler;


    public Response<List<AccountResponseDto>> findALlAccounts() {
        return requestsHandler.okReq(accountCommand.findALl());
    }


    public Response<AccountResponseDto> findAccountById(@PathVariable int accountId) {
        return requestsHandler.okReq(accountCommand.findById(accountId));
    }

    public Response<AccountResponseDto> registerAccount(@RequestBody AccountsDto accountsDto) {
        return requestsHandler.okReq(accountCommand.registerAccount(accountsDto));
    }

    public Response<AccountResponseDto> addUser(@PathVariable int accountId, @PathVariable int userId) {
        return requestsHandler.okReq(accountCommand.addUser(accountId, userId));
    }

    public Response<AccountResponseDto> deposit ( @PathVariable int accountId, @PathVariable double balance){
        return requestsHandler.okReq(accountCommand.depositMoney(balance, accountId));
    }

    public Response<AccountResponseDto> withdraw (@PathVariable int accountId, @PathVariable double balance){
        return requestsHandler.okReq(accountCommand.withdrawMoney(balance, accountId));
    }
}
