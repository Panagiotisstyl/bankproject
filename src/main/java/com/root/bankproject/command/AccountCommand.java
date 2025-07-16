package com.root.bankproject.command;

import com.root.bankproject.converters.AccountsConverter;
import com.root.bankproject.dtos.AccountResponseDto;
import com.root.bankproject.dtos.AccountsDto;
import com.root.bankproject.entities.Account;
import com.root.bankproject.services.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountCommand {

    private final AccountsService accountsService;
    private final AccountsConverter accountsConverter;



    public List<AccountResponseDto> findALl(){
        return accountsConverter.toDtoList(accountsService.findAll());
    }

    public AccountResponseDto findById(int accId){
        return accountsConverter.toResponseDto(accountsService.findById(accId));
    }

    public AccountResponseDto registerAccount(AccountsDto accountsDto){
        return accountsConverter.toResponseDto(accountsService.save(accountsConverter.toEntity(accountsDto)));
    }

    public AccountResponseDto addUser(int accountId, int userId){
        return accountsConverter.toResponseDto(accountsService.addUser(accountId,userId));
    }

    public AccountResponseDto depositMoney(double balance, int accountId) {
        Account account=accountsService.findById(accountId);
        account.depositBalance(balance);
        return accountsConverter.toResponseDto(accountsService.save(account));

    }

    public AccountResponseDto withdrawMoney(double moneyToWithdraw, int accountId) {
        Account account=accountsService.findById(accountId);
        account.withdrawal(moneyToWithdraw);
        return accountsConverter.toResponseDto(accountsService.save(account));
    }

}
