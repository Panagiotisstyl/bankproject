package com.root.bankproject.controllers;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AccountsRestController {

    private final AccountsConverter accountsConverter;

    @GetMapping("/accounts")
    public List<AccountResponseDto> findALlAccounts() {
        return accountsConverter.findALlConvert();
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDto findAccountById(@PathVariable int accountId) {
        return accountsConverter.findByIdConvert(accountId);
    }

    @PostMapping("/accounts")
    public AccountResponseDto registerAccount(@RequestBody AccountsDto accountsDto) {
        return accountsConverter.registerAccountConvert(accountsDto);
    }

    @PostMapping("/accounts/addUser/{userId}")
    public void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId) {
        accountsConverter.addUserConvert(accountResponseDto,userId);

    }
}
