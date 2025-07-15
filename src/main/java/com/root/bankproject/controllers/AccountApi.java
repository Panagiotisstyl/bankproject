package com.root.bankproject.controllers;

import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/addUser/{userId}")
    void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId);

}
