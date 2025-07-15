package com.root.bankproject.controllers;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.services.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//TODO: from all controllers call command
//TODO: all converters to have an interface
@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AccountsRestController {

    private final AccountsConverter accountsConverter;
    private final AccountsService accountsService;

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
        return accountsConverter.toResponseDto(accountsService.findById());
        return accountsConverter.registerAccountConvert(accountsDto);
    }

    @PostMapping("/accounts/addUser/{userId}")
    public void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId) {
        accountsConverter.addUserConvert(accountResponseDto,userId);

    }
}
