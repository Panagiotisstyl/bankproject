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

    public void addUser(int accountId, int userId){
        accountsService.addUser(accountId,userId);
    }

    public void depositMoney(double balance, int accountId) {
        Account account=accountsService.findById(accountId);
        account.depositBalance(balance);
        accountsService.save(account);

    }

    public void withdrawMoney(double moneyToWithdraw, int accountId) {
        Account account=accountsService.findById(accountId);
        account.withdrawal(moneyToWithdraw);
        accountsService.save(account);
    }

}
