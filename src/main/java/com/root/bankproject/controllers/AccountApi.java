package com.root.bankproject.controllers;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AccountApi {

    @GetMapping("/accounts")
    List<AccountResponseDto> findALlAccounts();

    @GetMapping("/accounts/{accountId}")
    AccountResponseDto findAccountById(@PathVariable int accountId);

    @PostMapping("/accounts")
    AccountResponseDto registerAccount(@RequestBody AccountsDto accountsDto);

    @PostMapping("/accounts/addUser/{userId}")
    void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId);

}
