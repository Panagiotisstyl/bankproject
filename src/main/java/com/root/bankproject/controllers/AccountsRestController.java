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

    @PostMapping("/accounts/addUser/{accountId}")
    public void addUser(@RequestBody int userId, @PathVariable int accountId) {
        accountsConverter.addUserConvert(accountId,userId);

    }

    @PostMapping("/accounts/deposit/{accountId}")
    public void deposit(@RequestBody double balance, @PathVariable int accountId) {
        accountsConverter.depositMoney(balance,accountId);
    }

    @PostMapping("/accounts/withdraw/{accountId}")
    public void withdraw(@RequestBody double balance, @PathVariable int accountId) {
        accountsConverter.withdrawMoney(balance,accountId);
    }
}
