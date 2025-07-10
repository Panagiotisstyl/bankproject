package com.root.bankproject.controllers;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Accounts;
import com.root.bankproject.entities.Users;
import com.root.bankproject.enums.TypeAccount;
import com.root.bankproject.services.AccountsService;
import com.root.bankproject.services.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class AccountsRestController {

    private final AccountsService accountsService;
    private final AccountsConverter accountsConverter;
    private final UsersService usersService;

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

        Accounts account= accountsService.findById(accountResponseDto.getId());
        if(account.getTypeAccount()== TypeAccount.SINGLE){
            throw new RuntimeException("Cannot add user, account type is single");
        }
        List<Users> newLIst=new ArrayList<>();
        newLIst.add(usersService.findById(userId));

        AccountsDto dto=AccountsDto.builder()
                .typeAccount(account.getTypeAccount())
                .description(account.getDescription())
                .balance(account.getBalance())
                .ids(AccountsConverter.getIds(newLIst))
                .build();

        Accounts accToUpdate=accountsConverter.toEntity(dto,account);

        accountsService.save(accToUpdate);



    }
}
