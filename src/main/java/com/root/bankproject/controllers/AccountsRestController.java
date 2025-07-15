package com.root.bankproject.controllers;

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


    public List<AccountResponseDto> findALlAccounts() {
        return accountCommand.findALl();
    }


    public AccountResponseDto findAccountById(@PathVariable int accountId) {
        return accountCommand.findById(accountId);
    }

    public AccountResponseDto registerAccount(@RequestBody AccountsDto accountsDto) {
        return accountCommand.registerAccount(accountsDto);
    }

    public void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId) {
        accountCommand.addUser(accountResponseDto,userId);

    @PostMapping("/accounts/deposit/{accountId}")
    public void deposit(@RequestBody double balance, @PathVariable int accountId) {
        accountsConverter.depositMoney(balance,accountId);
    }

    @PostMapping("/accounts/withdraw/{accountId}")
    public void withdraw(@RequestBody double balance, @PathVariable int accountId) {
        accountsConverter.withdrawMoney(balance,accountId);
    }
}
