package com.root.bankproject.controllers;

import com.root.bankproject.ExceptionHandler.Response;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("api/v1/accounts")
public interface AccountApi {

    @GetMapping
    Response<List<AccountResponseDto>> findALlAccounts();

    @GetMapping("/{accountId}")
    Response<AccountResponseDto> findAccountById(@PathVariable int accountId);

    @PostMapping
    Response<AccountResponseDto> registerAccount(@RequestBody AccountsDto accountsDto);

    @PostMapping("/addUser/{accountId}/{userId}")
    Response<AccountResponseDto> addUser(@PathVariable int accountId, @PathVariable int userId);

    @PostMapping("/deposit/{accountId}/{balance}")
    Response<AccountResponseDto> deposit(@PathVariable int accountId, @PathVariable double balance);

    @PostMapping("/withdraw/{accountId}/{balance}")
    Response<AccountResponseDto> withdraw(@PathVariable int accountId, @PathVariable double balance);

}
