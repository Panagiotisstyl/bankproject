package com.root.bankproject.controllers;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Accounts;
import com.root.bankproject.services.AccountsService;
import com.root.bankproject.validations.AccountValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AccountsRestController {

    private final AccountsService accountsService;
    private final AccountsConverter accountsConverter;
    private final AccountValidation accountValidation;

    @GetMapping("/accounts")
    public List<AccountResponseDto> findALlAccounts() {
        return accountsConverter.toDtoList(accountsService.findAll());
    }

    @GetMapping("/accounts/{accountId}")
    public AccountResponseDto findAccountById(@PathVariable int accountId) {
        return accountsConverter.toResponseDto(accountsService.findById(accountId));
    }

    @PostMapping("/accounts")
    public AccountResponseDto registerAccount(@RequestBody AccountsDto accountsDto) {
        return accountsConverter.toResponseDto(accountsService.save(accountsConverter.toEntity(accountsDto)));
    }

    @PostMapping("/accounts/addUser/{userId}")
    public void addUser(@RequestBody AccountResponseDto accountResponseDto, @PathVariable int userId) {
        
        AccountsDto dto=accountValidation.validateUsersAccount(accountResponseDto,userId);
        Accounts accToUpdate=accountsConverter.toEntity(dto, accountsService.findById(accountResponseDto.getId()));
        accountsService.save(accToUpdate);



    }
}
