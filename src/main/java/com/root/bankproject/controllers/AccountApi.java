package com.root.bankproject.controllers;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("api/v1/accounts")
public interface AccountApi {

    @GetMapping
    List<AccountResponseDto> findALlAccounts();

    @GetMapping("/{accountId}")
    AccountResponseDto findAccountById(@PathVariable int accountId);

    @PostMapping
    AccountResponseDto registerAccount(@RequestBody AccountsDto accountsDto);

    @PostMapping("/addUser/{accountId}/{userId}")
    void addUser(@PathVariable int accountId, @PathVariable int userId);

    @PostMapping("/deposit/{accountId}/{balance}")
    void deposit(@PathVariable int accountId, @PathVariable double balance);

    @PostMapping("/withdraw/{accountId}/{balance}")
    void withdraw(@PathVariable int accountId, @PathVariable double balance);

}
